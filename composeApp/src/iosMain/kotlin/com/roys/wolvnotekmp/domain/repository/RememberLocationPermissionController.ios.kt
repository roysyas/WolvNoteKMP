package com.roys.wolvnotekmp.domain.repository

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorized
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusDenied
import platform.darwin.NSObject

@Composable
actual fun RememberLocationPermissionController(): LocationPermissionController {
    val state = remember { MutableStateFlow(false) }
    val manager = remember { CLLocationManager() }

    DisposableEffect(Unit) {
        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(
                manager: CLLocationManager,
                didChangeAuthorizationStatus: CLAuthorizationStatus
            ) {
                state.value = getStatus()
            }
        }
        manager.delegate = delegate
        onDispose { manager.delegate = null }
    }

    return remember {
        object : LocationPermissionController {
            override val status: StateFlow<Boolean> = state
            override fun requestPermission() {
                manager.requestWhenInUseAuthorization()
            }
        }
    }
}

private fun getStatus(): Boolean =
    when (CLLocationManager.authorizationStatus()) {
        kCLAuthorizationStatusAuthorized,
        kCLAuthorizationStatusAuthorizedAlways,
        kCLAuthorizationStatusAuthorizedWhenInUse -> true
        kCLAuthorizationStatusDenied -> false
        else -> false
    }