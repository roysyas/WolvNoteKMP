package com.roys.wolvnotekmp.data.repository

import com.roys.wolvnotekmp.domain.repository.ILocationProvider
import com.roys.wolvnotekmp.domain.model.Location

actual class LocationRepositoryImpl: ILocationProvider {
    actual override suspend fun getCurrentLocation(): Location? {
        return null
    }
}