package com.roys.wolvnotekmp.presentation.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackBarError(
    val message: String
)

object SnackBarController {
    private val _events = Channel<SnackBarError>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackBarError){
        _events.send(event)
    }
}