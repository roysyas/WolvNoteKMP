package com.roys.wolvnotekmp.domain.repository

import com.roys.wolvnotekmp.data.remote.model.TemperatureResponse

interface WeatherRepository {
    suspend fun getWeather(latitude: Double, longitude: Double, timezone: String): TemperatureResponse
}