package com.roys.wolvnotekmp.domain.repository

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
actual fun RememberLocationPermissionController(): LocationPermissionController {
    val state = remember { MutableStateFlow(true) }
    return object : LocationPermissionController {
        override val status: StateFlow<Boolean> = state
        override fun requestPermission() {}
    }
}