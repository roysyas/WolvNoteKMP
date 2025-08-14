package com.roys.wolvnotekmp.data.repository

import com.roys.wolvnotekmp.data.database.NoteDao
import com.roys.wolvnotekmp.data.database.NoteTable
import com.roys.wolvnotekmp.domain.repository.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDao): NoteRepository {
    override suspend fun insertNote(noteTable: NoteTable) {
        noteDao.insertNote(noteTable)
    }

    override suspend fun deleteNote(noteTable: NoteTable) {
        noteDao.deleteNote(noteTable)
    }

    override suspend fun getNotes(): List<NoteTable> {
        return noteDao.getNotes()
    }

    override suspend fun getNote(id: Int): NoteTable {
        return noteDao.getNote(id)
    }

    override suspend fun updateNote(noteTable: NoteTable) {
        noteDao.updateNote(noteTable)
    }
}