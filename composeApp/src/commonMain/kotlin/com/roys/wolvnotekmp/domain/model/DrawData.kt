package com.roys.wolvnotekmp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DrawData (
    val contentData: DrawContentData = DrawContentData(),
    val noteTitle: String = "",
    val createDate: String = "",
    val noteId: Int? = null,
    val lastEditDate: String? = null
)