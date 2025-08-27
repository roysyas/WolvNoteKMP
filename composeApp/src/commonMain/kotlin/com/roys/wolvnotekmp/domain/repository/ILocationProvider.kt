package com.roys.wolvnotekmp.domain.repository

import com.roys.wolvnotekmp.domain.model.Location

interface ILocationProvider {
    suspend fun getCurrentLocation(): Location?
}