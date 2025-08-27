package com.roys.wolvnotekmp.data.remote

import com.roys.wolvnotekmp.data.remote.model.TemperatureResponse

interface ApiService {
    suspend fun getCurrentWeather(latitude: Double, longitude: Double, timezone: String): TemperatureResponse
}