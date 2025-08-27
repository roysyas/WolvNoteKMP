package com.roys.wolvnotekmp.domain.repository

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
actual fun RememberLocationPermissionController(): LocationPermissionController {
    val state = remember { MutableStateFlow(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            state.value = granted
        }
    )

    return remember {
        object : LocationPermissionController {
            override val status: StateFlow<Boolean> = state
            override fun requestPermission() {
                launcher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }
}