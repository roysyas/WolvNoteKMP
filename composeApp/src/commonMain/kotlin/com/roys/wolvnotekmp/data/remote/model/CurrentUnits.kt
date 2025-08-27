package com.roys.wolvnote.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrentUnits(
    val interval: String,
    val temperature_2m: String,
    val time: String,
    val weather_code: String
)