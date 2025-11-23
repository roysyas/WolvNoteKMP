package com.roys.wolvnotekmp.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.roys.wolvnotekmp.domain.model.NoteTable
import com.roys.wolvnotekmp.domain.model.PasswordTable

@Database(
    entities = [NoteTable::class, PasswordTable::class],
    version = 1,
    exportSchema = false,
    autoMigrations = []
)

@ConstructedBy(DatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun passwordDao(): PasswordDao

    companion object {
        const val DB_NAME = "appDatabase.db"
    }
}