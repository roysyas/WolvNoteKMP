package com.roys.wolvnote.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Current(
    val interval: Int,
    val temperature_2m: Double,
    val time: String,
    val weather_code: Int
)