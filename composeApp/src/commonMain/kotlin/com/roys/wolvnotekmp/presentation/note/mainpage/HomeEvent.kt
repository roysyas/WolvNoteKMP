package com.roys.wolvnotekmp.presentation.note.mainpage

sealed interface HomeEvent {
    data class OnToggle(val toggle: Boolean): HomeEvent
    data class OnRefresh(val refresh: Boolean): HomeEvent
    data class OnDelete(val item: Int?): HomeEvent
    data class RequestPermission(val granted: Boolean): HomeEvent
}