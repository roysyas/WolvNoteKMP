package com.roys.wolvnotekmp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val noteId: Int? = null,
    @ColumnInfo(name = "create_date")
    val noteCreateDate: String = "",
    @ColumnInfo(name = "title")
    val noteTitle: String = "",
    @ColumnInfo(name = "content")
    val noteContent: String = "",
    @ColumnInfo(name = "category")
    val noteCategory: Int = 0,
    @ColumnInfo(name = "last_edit_date")
    val noteLastEditDate: String? = null
)