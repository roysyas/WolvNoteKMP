package com.roys.wolvnotekmp.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

fun DatabaseFactoryJvm(): RoomDatabase.Builder<AppDatabase>{
    val os = System.getProperty("os.name").lowercase()
    val userHome = System.getProperty("user.home")
    val appDataDir = when {
        os.contains("win") -> File(System.getenv("APPDATA"), "WolvNoteKmp")
        os.contains("mac") -> File(userHome, "Library/Application Support/WolvNoteKmp")
        else -> File(userHome, ".local/share/WolvNoteKmp")
    }

    if (!appDataDir.exists()) {
        appDataDir.mkdirs()
    }

    val dbFile = File(appDataDir, AppDatabase.DB_NAME)
    return Room.databaseBuilder<AppDatabase>(dbFile.absolutePath)
}