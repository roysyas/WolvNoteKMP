package com.roys.wolvnotekmp.data.repository

import com.roys.wolvnotekmp.data.database.PasswordDao
import com.roys.wolvnotekmp.domain.model.PasswordTable
import com.roys.wolvnotekmp.domain.repository.PasswordRepository

class PasswordRepositoryImpl (private val passwordDao: PasswordDao): PasswordRepository {
    override suspend fun insertPassword(passwordTable: PasswordTable) {
        passwordDao.insertPassword(passwordTable)
    }

    override suspend fun getPassword(): PasswordTable? {
        return passwordDao.getPassword()
    }
}