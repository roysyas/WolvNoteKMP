package com.roys.wolvnotekmp.presentation.note.mainpage

import com.roys.wolvnotekmp.domain.model.Location
import com.roys.wolvnotekmp.domain.model.NoteTable
import com.roys.wolvnotekmp.domain.model.CurrentWeather


data class HomeState(
    val isLoading: Boolean = false,
    val error: String = "",
    val isToggle: Boolean = false,
    val noteList: List<NoteTable> = listOf(),
    val isGranted: Boolean = false,
    val location: Location? = null,
    val currentWeather: CurrentWeather? = null,
    val weatherError: String = "",
    val weatherIsLoading: Boolean = false
)