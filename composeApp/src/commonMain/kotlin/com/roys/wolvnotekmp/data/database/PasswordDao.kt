package com.roys.wolvnotekmp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PasswordDao {
    @Insert
    suspend fun insertPassword(passwordTable: PasswordTable)

    @Query("SELECT * FROM password_table ORDER BY password_id DESC LIMIT 1")
    suspend fun getPassword(): PasswordTable?
}