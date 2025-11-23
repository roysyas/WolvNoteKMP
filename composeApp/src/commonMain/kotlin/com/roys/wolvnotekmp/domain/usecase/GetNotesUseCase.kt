package com.roys.wolvnotekmp.domain.usecase

import com.roys.wolvnotekmp.common.Resource
import com.roys.wolvnotekmp.domain.model.NoteTable
import com.roys.wolvnotekmp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNotesUseCase (
    private val noteRepository: NoteRepository
) {
    operator fun invoke(): Flow<Resource<List<NoteTable>>> = flow {
        try {
            emit(Resource.Loading())
            val notes = noteRepository.getNotes()
            emit(Resource.Success(notes))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}