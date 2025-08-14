package com.roys.wolvnotekmp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(noteTable: NoteTable)

    @Delete
    suspend fun deleteNote(noteTable: NoteTable)

    @Query("SELECT * FROM note_table")
    suspend fun getNotes(): List<NoteTable>

    @Query("SELECT * FROM note_table WHERE note_id=:noteId")
    suspend fun getNote(noteId: Int): NoteTable

    @Update
    suspend fun updateNote(noteTable: NoteTable)
}