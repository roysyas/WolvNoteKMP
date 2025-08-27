package com.roys.wolvnotekmp.presentation.note.mainpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roys.wolvnotekmp.common.DateTimeHelper
import com.roys.wolvnotekmp.domain.repository.ILocationProvider
import com.roys.wolvnotekmp.common.Resource
import com.roys.wolvnotekmp.data.database.NoteTable
import com.roys.wolvnotekmp.domain.usecase.DeleteNoteUseCase
import com.roys.wolvnotekmp.domain.usecase.GetNoteUseCase
import com.roys.wolvnotekmp.domain.usecase.GetNotesUseCase
import com.roys.wolvnotekmp.domain.usecase.WeatherUseCase
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
    private val getNoteUseCase: GetNoteUseCase,
    private val locationProvider: ILocationProvider,
    private val weatherUseCase: WeatherUseCase
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
            is HomeEvent.RequestPermission -> fetchLocation(viewEvent.granted)
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

    private fun fetchLocation(granted: Boolean){
        if(granted){
            viewModelScope.launch {
                val loc = locationProvider.getCurrentLocation()
                _state.update {
                    it.copy(
                        location = loc
                    )
                }
                getWeather()
            }
        }
    }

    private fun getWeather(){
        val lat = _state.value.location?.lat
        val lng = _state.value.location?.lng
        val timezone = DateTimeHelper.getTimeZone()

        lat?.let {
            lng?.let {
                weatherUseCase.invoke(lat, lng, timezone).onEach { result->
                    when(result){
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    weatherError = result.message ?: "An unexpected error occurred"
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _state.update {
                                it.copy(
                                    weatherIsLoading = true
                                )
                            }
                        }
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    currentWeather = result.data
                                )
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}