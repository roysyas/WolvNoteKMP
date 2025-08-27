package com.roys.wolvnotekmp.presentation.note.mainpage.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.roys.wolvnotekmp.domain.repository.RememberLocationPermissionController

@Composable
fun LocationPermission(
    onGranted: (Boolean) -> Unit
){

    val controller = RememberLocationPermissionController()
    val status by controller.status.collectAsState()

    when(status){
        true -> onGranted(status)
        false -> {
            LaunchedEffect(status) {
                controller.requestPermission()
            }
        }
    }

}