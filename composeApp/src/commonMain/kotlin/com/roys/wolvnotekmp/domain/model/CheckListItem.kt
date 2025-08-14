package com.roys.wolvnotekmp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CheckListItem (
    val checked: Boolean,
    val text: String,
    val id: String
)