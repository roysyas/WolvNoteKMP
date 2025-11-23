package com.roys.wolvnotekmp.domain.repository

import com.roys.wolvnotekmp.domain.model.PasswordTable

interface PasswordRepository {
    suspend fun insertPassword(passwordTable: PasswordTable)
    suspend fun getPassword(): PasswordTable?
}