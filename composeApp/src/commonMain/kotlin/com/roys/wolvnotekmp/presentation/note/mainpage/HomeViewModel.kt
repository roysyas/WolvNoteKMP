package com.roys.wolvnotekmp.presentation.note.mainpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roys.wolvnotekmp.common.Resource
import com.roys.wolvnotekmp.data.database.NoteTable
import com.roys.wolvnotekmp.domain.usecase.DeleteNoteUseCase
import com.roys.wolvnotekmp.domain.usecase.GetNoteUseCase
import com.roys.wolvnotekmp.domain.usecase.GetNotesUseCase
import com.roys.wolvnotekmp.presentation.util.SnackBarController
import com.roys.wolvnotekmp.presentation.util.SnackBarError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        getNotes()
    }

    fun handleEvent(viewEvent: HomeEvent){
        when(viewEvent){
            is HomeEvent.OnToggle -> onToggle(viewEvent.toggle)
            is HomeEvent.OnRefresh -> getNotes()
            is HomeEvent.OnDelete -> prepareDeleteNote(viewEvent.item)
        }
    }

    private fun onToggle(toggle: Boolean){
        _state.update {
            it.copy(
                isToggle = toggle
            )
        }
    }

    private fun prepareDeleteNote(item: Int?){
        item?.let { getNote(it) }
    }

    private fun deleteNote(item: NoteTable){
        deleteNoteUseCase.invoke(item).onEach { result->
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
                    _state.value = HomeState(isLoading = true)
                }
                is Resource.Success -> {
                    getNotes()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getNotes(){
        getNotesUseCase.invoke().onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = HomeState(isLoading = true)
                }
                is Resource.Success -> {
                    if(result.data.isNullOrEmpty()){
                        _state.value = HomeState(isEmpty = true)
                    }else{
                        _state.value = HomeState(noteList = result.data)
                    }
                }
                is Resource.Error -> {
                    _state.value =
                        HomeState(error = result.message ?: "An unexpected error occurred")
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
                        deleteNote(result.data)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}