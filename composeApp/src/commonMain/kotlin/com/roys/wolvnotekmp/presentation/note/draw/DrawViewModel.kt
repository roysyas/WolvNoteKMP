package com.roys.wolvnotekmp.presentation.note.draw

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roys.wolvnotekmp.common.Constants
import com.roys.wolvnotekmp.common.DateTimeHelper
import com.roys.wolvnotekmp.common.ExportToImageFileHelper
import com.roys.wolvnotekmp.common.Resource
import com.roys.wolvnotekmp.common.exportToImageFile
import com.roys.wolvnotekmp.domain.model.NoteTable
import com.roys.wolvnotekmp.domain.model.DrawContentData
import com.roys.wolvnotekmp.domain.model.PathData
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
import kotlin.time.Clock

class DrawViewModel (
    private val insertNoteUseCase: InsertNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _state = MutableStateFlow(DrawState())
    val state: StateFlow<DrawState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>(Constants.NOTE_ID)?.let { noteId->
            getNote(noteId.toInt())
            _state.update {
                it.copy(
                    isEdit = true,
                    drawData = _state.value.drawData.copy(
                        noteId = noteId.toInt()
                    )
                )
            }
        }
    }

    fun handleEvent(viewEvent: DrawEvent){
        when(viewEvent){
            is DrawEvent.TitleUpdate -> titleUpdate(viewEvent.title)
            is DrawEvent.OnClearCanvasClick -> onClearCanvasClick()
            is DrawEvent.OnDraw -> onDraw(viewEvent.offset)
            is DrawEvent.OnNewPathStart -> onNewPathStart()
            is DrawEvent.OnPathEnd -> onPathEnd()
            is DrawEvent.OnSelectColor -> onSelectColor(viewEvent.color)
            is DrawEvent.InsertNote -> validateInsert()
            is DrawEvent.OnSelectWeight -> onSelectWeight(viewEvent.weight)
            is DrawEvent.OnGetSize -> onGetSize(viewEvent.size)
            is DrawEvent.InsertAndExport -> validateExport()
        }
    }

    private fun validateInsert(){
        if(_state.value.drawData.contentData.pathDataList.isNotEmpty()){
            if(_state.value.isEdit){
                prepareUpdate()
            }else{
                prepareInsert()
            }
        }
    }

    private fun validateExport(){
        if(_state.value.drawData.contentData.pathDataList.isNotEmpty()){
            if(_state.value.isEdit){
                prepareUpdateExport()
            }else{
                prepareInsertExport()
            }
        }
    }

    private fun titleUpdate(title: String){
        _state.update {
            it.copy(
                drawData = _state.value.drawData.copy(
                    noteTitle = title
                )
            )
        }
    }

    private fun onSelectColor(color: Color) {
        _state.update { it.copy(
            selectedColor = color
        ) }
    }

    private fun onGetSize(size: IntSize){
        _state.update {
            it.copy(
                drawData = _state.value.drawData.copy(
                    contentData = _state.value.drawData.contentData.copy(
                        size = size
                    )
                )
            )
        }
    }

    private fun onSelectWeight(weight: Float){
        _state.update {
            it.copy(
                selectedWeight = weight
            )
        }
    }

    private fun onPathEnd() {
        val currentPathData = _state.value.currentPath ?: return
        val items = _state.value.drawData.contentData.pathDataList.toMutableList().apply {
            add(currentPathData)
        }.toList()
        _state.update { it.copy(
            currentPath = null,
            drawData = _state.value.drawData.copy(
                contentData = _state.value.drawData.contentData.copy(
                    pathDataList = items
                )
            )
        ) }
    }

    private fun onNewPathStart() {
        _state.update { it.copy(
            currentPath = PathData(
                id = DateTimeHelper.getId(),
                color = it.selectedColor,
                path = emptyList(),
                weight = it.selectedWeight
            )
        ) }
    }

    private fun onDraw(offset: Offset) {
        val currentPathData = _state.value.currentPath ?: return
        _state.update { it.copy(
            currentPath = currentPathData.copy(
                path = currentPathData.path + offset
            )
        ) }
    }

    private fun onClearCanvasClick() {
        _state.update { it.copy(
            currentPath = null,
            drawData = _state.value.drawData.copy(
                contentData = _state.value.drawData.contentData.copy(
                    pathDataList = emptyList()
                )
            )
        ) }
    }

    private fun prepareUpdate(){
        val noteContent = Json.encodeToString( _state.value.drawData.contentData)
        val noteTable = NoteTable(
            noteId = _state.value.drawData.noteId,
            noteCreateDate = _state.value.drawData.createDate,
            noteLastEditDate = DateTimeHelper.getCurrentDateTime(),
            noteTitle = _state.value.drawData.noteTitle,
            noteContent = noteContent,
            noteCategory = Constants.CATEGORY_DRAW
        )
        updateNote(noteTable)
    }

    private fun prepareUpdateExport(){
        _state.value.drawData.contentData.size?.let { size ->
            exportToImageFile(size, _state.value.drawData.noteTitle, _state.value.drawData.contentData.pathDataList)
            prepareUpdate()
        }
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

    private fun prepareInsert(){
        val noteContent = Json.encodeToString( _state.value.drawData.contentData)
        val noteTable = NoteTable(
            noteCreateDate = DateTimeHelper.getCurrentDateTime(),
            noteTitle = _state.value.drawData.noteTitle,
            noteContent = noteContent,
            noteCategory = Constants.CATEGORY_DRAW
        )
        insertNote(noteTable)
    }

    private fun prepareInsertExport(){
        _state.value.drawData.contentData.size?.let { size ->
            exportToImageFile(size, _state.value.drawData.noteTitle, _state.value.drawData.contentData.pathDataList)
            prepareInsert()
        }
    }

    private fun insertNote(noteTable: NoteTable){
        insertNoteUseCase.invoke(noteTable).onEach { result ->
            when (result) {
                is Resource.Loading -> {
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
                    result.data?.noteContent?.let {
                        val resData = Json.decodeFromString<DrawContentData>(result.data.noteContent)
                        _state.update {
                            it.copy(
                                drawData = _state.value.drawData.copy(
                                    contentData = resData,
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