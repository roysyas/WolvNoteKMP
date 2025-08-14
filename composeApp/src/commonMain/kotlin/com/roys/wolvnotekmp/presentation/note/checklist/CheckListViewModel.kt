package com.roys.wolvnotekmp.presentation.note.checklist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roys.wolvnotekmp.common.Constants
import com.roys.wolvnotekmp.common.DateTimeHelper
import com.roys.wolvnotekmp.common.Resource
import com.roys.wolvnotekmp.data.database.NoteTable
import com.roys.wolvnotekmp.domain.model.CheckListItem
import com.roys.wolvnotekmp.domain.usecase.GetNoteUseCase
import com.roys.wolvnotekmp.domain.usecase.InsertNoteUseCase
import com.roys.wolvnotekmp.domain.usecase.UpdateNoteUseCase
import com.roys.wolvnotekmp.presentation.util.SnackBarController
import com.roys.wolvnotekmp.presentation.util.SnackBarError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CheckListViewModel(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(CheckListState())
    val state: StateFlow<CheckListState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>(Constants.NOTE_ID)?.let { noteId ->
            getNote(noteId.toInt())
            _state.update {
                it.copy(
                    isEdit = true,
                    checkListData = _state.value.checkListData.copy(noteId = noteId.toInt())
                )
            }
        }
    }

    fun handleEvent(viewEvent: CheckListEvent){
        when(viewEvent){
            is CheckListEvent.AddItem -> addItem(viewEvent.checkListItem)
            is CheckListEvent.RemoveItem -> removeItem(viewEvent.checkListItem)
            is CheckListEvent.TitleUpdate -> titleUpdate(viewEvent.title)
            is CheckListEvent.InsertNote -> validateInsert()
            is CheckListEvent.CheckItem -> checkItem(viewEvent.checkListItem)
        }
    }

    private fun validateInsert(){
        if(_state.value.isEdit){
            prepareUpdateData()
        }else{
            prepareInsertData()
        }
    }

    private fun addItem(item: CheckListItem){
        val items = _state.value.checkListData.contentData.toMutableList().apply {
            add(item)
        }.toList()
        _state.update {
            it.copy(
                checkListData = _state.value.checkListData.copy(items),
            )
        }
    }

    private fun removeItem(item: CheckListItem){
        val items = _state.value.checkListData.contentData.toMutableList().apply {
            remove(item)
        }.toList()
        _state.update {
            it.copy(
                checkListData = _state.value.checkListData.copy(items)
            )
        }
    }

    private fun titleUpdate(title: String){
        _state.update {
            it.copy(
                checkListData = _state.value.checkListData.copy(
                    noteTitle = title
                )
            )
        }
    }

    private fun checkItem(item: CheckListItem){
        val index = _state.value.checkListData.contentData.indexOf(item)
        val newItem = item.copy(
            checked = !item.checked
        )
        val items = _state.value.checkListData.contentData.toMutableList().apply {
            remove(item)
            add(index, newItem)
        }.toList()
        _state.update {
            it.copy(
                checkListData = _state.value.checkListData.copy(items)
            )
        }
    }

    private fun prepareInsertData(){
        val parsedData = Json.encodeToString(_state.value.checkListData.contentData)
        val noteTable = NoteTable(
            noteCreateDate = DateTimeHelper.getCurrentDateTime(),
            noteTitle = _state.value.checkListData.noteTitle,
            noteContent = parsedData,
            noteCategory = Constants.CATEGORY_CHECKLIST
        )
        insertNote(noteTable)
    }

    private fun insertNote(noteTable: NoteTable){
        insertNoteUseCase.invoke(noteTable).onEach { result ->
            when(result){
                is Resource.Loading ->{
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.Error -> {
                    viewModelScope.launch {
                        SnackBarController.sendEvent(
                            event = SnackBarError(
                                message = result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                }
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isSuccess = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun prepareUpdateData(){
        val noteContent = Json.encodeToString( _state.value.checkListData.contentData)
        val noteTable = NoteTable(
            noteId = _state.value.checkListData.noteId,
            noteCreateDate = _state.value.checkListData.createDate,
            noteLastEditDate = DateTimeHelper.getCurrentDateTime(),
            noteTitle = _state.value.checkListData.noteTitle,
            noteContent = noteContent,
            noteCategory = Constants.CATEGORY_CHECKLIST
        )
        updateNote(noteTable)
    }

    private fun updateNote(noteTable: NoteTable){
        updateNoteUseCase.invoke(noteTable).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    viewModelScope.launch {
                        SnackBarController.sendEvent(
                            event = SnackBarError(
                                message = result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isSuccess = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getNote(id: Int){
        getNoteUseCase.invoke(id).onEach { result->
            when(result){
                is Resource.Error -> {
                    viewModelScope.launch {
                        SnackBarController.sendEvent(
                            event = SnackBarError(
                                message = result.message ?: "An unexpected error occurred"
                            )
                        )
                    }
                }
                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
                is Resource.Success -> {
                    result.data?.noteContent?.let { resData ->
                        val parsedData = Json.decodeFromString<List<CheckListItem>>(resData)
                        _state.update {
                            it.copy(
                                checkListData = _state.value.checkListData.copy(
                                    contentData = parsedData,
                                    noteTitle = result.data.noteTitle,
                                    createDate = result.data.noteCreateDate,
                                    lastEditDate = result.data.noteLastEditDate
                                )
                            )
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}