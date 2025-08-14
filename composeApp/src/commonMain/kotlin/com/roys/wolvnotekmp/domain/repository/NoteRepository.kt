package com.roys.wolvnotekmp.domain.repository

import com.roys.wolvnotekmp.data.database.NoteTable

interface NoteRepository {
    suspend fun insertNote(noteTable: NoteTable)
    suspend fun deleteNote(noteTable: NoteTable)
    suspend fun getNotes(): List<NoteTable>
    suspend fun getNote(id: Int): NoteTable
    suspend fun updateNote(noteTable: NoteTable)
}