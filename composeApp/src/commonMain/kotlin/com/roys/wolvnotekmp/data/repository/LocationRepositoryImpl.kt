package com.roys.wolvnotekmp.data.repository

import com.roys.wolvnotekmp.domain.model.Location

expect class LocationRepositoryImpl {
    suspend fun getCurrentLocation(): Location?
}