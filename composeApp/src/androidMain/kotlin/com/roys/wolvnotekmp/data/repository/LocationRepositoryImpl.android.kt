package com.roys.wolvnotekmp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.roys.wolvnotekmp.domain.repository.ILocationProvider
import com.roys.wolvnotekmp.domain.model.Location
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("MissingPermission")
actual class LocationRepositoryImpl(private val context: Context): ILocationProvider {
    actual override suspend fun getCurrentLocation(): Location? {
        return suspendCancellableCoroutine { continuation ->
            val client = LocationServices.getFusedLocationProviderClient(context)
            client.lastLocation
                .addOnSuccessListener { location ->
                    val dataLocation = Location(location.latitude, location.longitude)
                    continuation.resume(dataLocation) { cause, _, _ -> }
                }
                .addOnFailureListener {
                    continuation.resume(null) { cause, _, _ -> }
                }
        }
    }
}