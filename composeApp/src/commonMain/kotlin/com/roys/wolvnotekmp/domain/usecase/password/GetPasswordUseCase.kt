package com.roys.wolvnotekmp.domain.usecase.password

import com.roys.wolvnotekmp.common.Resource
import com.roys.wolvnotekmp.data.database.PasswordTable
import com.roys.wolvnotekmp.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPasswordUseCase (
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(): Flow<Resource<PasswordTable>> = flow {
        try {
            emit(Resource.Loading())
            val password = passwordRepository.getPassword()
            emit(Resource.Success(password))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}