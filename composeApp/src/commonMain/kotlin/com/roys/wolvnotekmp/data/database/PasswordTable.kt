package com.roys.wolvnotekmp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password_table")
data class PasswordTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "password_id")
    val passwordId: Int?,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "hint")
    val hint: String
)
