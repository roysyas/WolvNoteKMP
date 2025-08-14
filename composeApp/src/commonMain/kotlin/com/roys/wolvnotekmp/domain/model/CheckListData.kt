package com.roys.wolvnotekmp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CheckListData (
    val contentData: List<CheckListItem> = emptyList(),
    val noteTitle: String = "",
    val noteId: Int? = null,
    val createDate: String = "",
    val lastEditDate: String? = null
)