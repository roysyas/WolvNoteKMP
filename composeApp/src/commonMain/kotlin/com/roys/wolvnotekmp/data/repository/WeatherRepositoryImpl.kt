package com.roys.wolvnotekmp.data.repository

import com.roys.wolvnotekmp.data.remote.ApiService
import com.roys.wolvnotekmp.data.remote.model.TemperatureResponse
import com.roys.wolvnotekmp.domain.repository.WeatherRepository

class WeatherRepositoryImpl (
    private val apiService: ApiService
): WeatherRepository {
    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        timezone: String
    ): TemperatureResponse {
        return apiService.getCurrentWeather(latitude,longitude,timezone)
    }

}