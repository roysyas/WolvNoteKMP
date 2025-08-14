package com.roys.wolvnotekmp.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING", "KotlinNoActualForExpect")
expect object DatabaseConstructor: RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}