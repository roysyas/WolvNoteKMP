package com.roys.wolvnotekmp.data.repository

import com.roys.wolvnotekmp.domain.repository.ILocationProvider
import com.roys.wolvnotekmp.domain.model.Location
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.Foundation.NSError
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
actual class LocationRepositoryImpl: ILocationProvider {
    private val manager = CLLocationManager()

    actual override suspend fun getCurrentLocation(): Location? = suspendCancellableCoroutine { cont ->
        val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
            override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                val last = didUpdateLocations.lastOrNull() as? CLLocation
                if (last != null) {
                    val loc = last.coordinate.useContents { Location(latitude, longitude) }
                    cont.resume(loc) { cause, _, _ -> }
                    manager.stopUpdatingLocation()
                } else {
                    cont.resume(null) { cause, _, _ -> }
                }
            }

            override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
                cont.resume(null) { cause, _, _ -> }
            }
        }

        manager.delegate = delegate
        manager.requestWhenInUseAuthorization()
        manager.startUpdatingLocation()
    }

}