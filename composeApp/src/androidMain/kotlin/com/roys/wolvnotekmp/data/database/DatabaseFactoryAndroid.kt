package com.roys.wolvnotekmp.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun DatabaseFactoryAndroid(context: Context): RoomDatabase.Builder<AppDatabase> {
    val aplContext = context.applicationContext
    val dbFile = aplContext.getDatabasePath(AppDatabase.DB_NAME)
    return Room.databaseBuilder<AppDatabase>(context, dbFile.absolutePath)
}