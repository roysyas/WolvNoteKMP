package com.roys.wolvnotekmp.domain.repository

import com.roys.wolvnotekmp.data.database.PasswordTable

interface PasswordRepository {
    suspend fun insertPassword(passwordTable: PasswordTable)
    suspend fun getPassword(): PasswordTable?
}