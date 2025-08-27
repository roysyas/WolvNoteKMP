package com.roys.wolvnotekmp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Location (
    val lat: Double? = null,
    val lng: Double? = null
)