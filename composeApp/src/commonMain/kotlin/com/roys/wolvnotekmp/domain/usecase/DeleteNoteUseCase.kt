package com.roys.wolvnotekmp.domain.usecase

import com.roys.wolvnotekmp.common.Resource
import com.roys.wolvnotekmp.data.database.NoteTable
import com.roys.wolvnotekmp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteNoteUseCase (
    private val noteRepository: NoteRepository
) {
    operator fun invoke(noteTable: NoteTable): Flow<Resource<NoteTable>> = flow {
        try {
            emit(Resource.Loading())
            noteRepository.deleteNote(noteTable)
            emit(Resource.Success(null))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}