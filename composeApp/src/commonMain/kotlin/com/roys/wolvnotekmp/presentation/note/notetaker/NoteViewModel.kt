package com.roys.wolvnotekmp.presentation.note.notetaker

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roys.wolvnotekmp.common.Constants
import com.roys.wolvnotekmp.common.DateTimeHelper
import com.roys.wolvnotekmp.common.Resource
import com.roys.wolvnotekmp.data.database.NoteTable
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
import kotlin.text.toInt

class NoteViewModel (
    private val insertNoteUseCase: InsertNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(NoteState())
    val state: StateFlow<NoteState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>(Constants.NOTE_ID)?.let { noteId ->
            getNote(noteId.toInt())
            _state.update {
                it.copy(
                    isEdit = true,
                    noteId = noteId.toInt()
                )
            }
        }
    }

    fun handleEvent(viewEvent: NoteEvent){
        when(viewEvent){
            is NoteEvent.InsertNote -> validateInsert()
            is NoteEvent.TitleUpdate -> titleUpdate(viewEvent.title)
            is NoteEvent.ContentUpdate -> contentUpdate(viewEvent.content)
        }
    }

    private fun validateInsert(){
        if(_state.value.notes.isNotEmpty()){
            if(_state.value.isEdit){
                prepareUpdateData()
            }else{
                prepareInsertData()
            }
        }
    }

    private fun titleUpdate(title: String){
        _state.update {
            it.copy(
                noteTitle = title
            )
        }
    }

    private fun contentUpdate(content: String){
        _state.update {
            it.copy(
                notes = content
            )
        }
    }

    private fun prepareInsertData(){
        val noteTable = NoteTable(
            noteCreateDate = DateTimeHelper.getCurrentDateTime(),
            noteTitle = _state.value.noteTitle,
            noteContent = _state.value.notes,
            noteCategory = Constants.CATEGORY_NOTE
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
        val noteTable = NoteTable(
            noteId = _state.value.noteId,
            noteCreateDate = _state.value.createDate,
            noteLastEditDate = DateTimeHelper.getCurrentDateTime(),
            noteTitle = _state.value.noteTitle,
            noteContent = _state.value.notes,
            noteCategory = Constants.CATEGORY_NOTE
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
                    result.data?.let {
                        _state.update {
                            it.copy(
                                notes = result.data.noteContent,
                                noteTitle = result.data.noteTitle,
                                createDate = result.data.noteCreateDate,
                                lastEditDate = result.data.noteLastEditDate
                            )
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}