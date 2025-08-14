package com.roys.wolvnotekmp.presentation.note.notetaker

data class NoteState (
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val noteTitle: String = "",
    val notes: String = "",
    val noteId: Int? = null,
    val isEdit: Boolean = false,
    val createDate: String = "",
    val lastEditDate: String? = null
)