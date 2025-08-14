package com.roys.wolvnotekmp.presentation.note.salarycalculation

data class SalaryState(
    val noteTitle: String = "",
    val notes: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isEdit: Boolean = false,
    val createDate: String = "",
    val noteId: Int? = null,
    val lastEditDate: String? = null
)
