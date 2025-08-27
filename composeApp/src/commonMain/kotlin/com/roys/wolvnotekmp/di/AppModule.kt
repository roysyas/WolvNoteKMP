package com.roys.wolvnotekmp.di

import androidx.lifecycle.SavedStateHandle
import com.roys.wolvnotekmp.domain.repository.ILocationProvider
import com.roys.wolvnotekmp.common.PreferenceDataStore
import com.roys.wolvnotekmp.presentation.auth.settingpassword.SettingPasswordViewModel
import com.roys.wolvnotekmp.data.database.AppDatabase
import com.roys.wolvnotekmp.data.database.DatabaseFactory
import com.roys.wolvnotekmp.data.remote.ApiService
import com.roys.wolvnotekmp.data.remote.KtorApiService
import com.roys.wolvnotekmp.data.repository.NoteRepositoryImpl
import com.roys.wolvnotekmp.data.repository.PasswordRepositoryImpl
import com.roys.wolvnotekmp.data.repository.WeatherRepositoryImpl
import com.roys.wolvnotekmp.domain.repository.NoteRepository
import com.roys.wolvnotekmp.domain.repository.PasswordRepository
import com.roys.wolvnotekmp.domain.repository.WeatherRepository
import com.roys.wolvnotekmp.domain.usecase.DeleteNoteUseCase
import com.roys.wolvnotekmp.domain.usecase.GetNoteUseCase
import com.roys.wolvnotekmp.domain.usecase.GetNotesUseCase
import com.roys.wolvnotekmp.domain.usecase.InsertNoteUseCase
import com.roys.wolvnotekmp.domain.usecase.UpdateNoteUseCase
import com.roys.wolvnotekmp.domain.usecase.WeatherUseCase
import com.roys.wolvnotekmp.domain.usecase.password.GetPasswordUseCase
import com.roys.wolvnotekmp.domain.usecase.password.InsertPasswordUseCase
import com.roys.wolvnotekmp.presentation.auth.login.LoginViewModel
import com.roys.wolvnotekmp.presentation.note.checklist.CheckListViewModel
import com.roys.wolvnotekmp.presentation.note.draw.DrawViewModel
import com.roys.wolvnotekmp.presentation.note.mainpage.HomeViewModel
import com.roys.wolvnotekmp.presentation.note.notetaker.NoteViewModel
import com.roys.wolvnotekmp.presentation.note.salarycalculation.SalaryViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val platformModule: Module
val appModule = module {
    //Database
    singleOf(::DatabaseFactory)
    single {get<AppDatabase>().passwordDao()}
    single {get<AppDatabase>().noteDao()}

    //KTOR
    single {
        HttpClient(get<HttpClientEngine>()) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }
    single<ApiService> { KtorApiService(get()) }

    //Repository
    single<PasswordRepository> { PasswordRepositoryImpl(get()) }
    single<NoteRepository> { NoteRepositoryImpl(get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }

    //UseCase
    single { GetPasswordUseCase(get<PasswordRepository>()) }
    single { InsertPasswordUseCase(get<PasswordRepository>()) }
    single { DeleteNoteUseCase(get<NoteRepository>()) }
    single { GetNoteUseCase(get<NoteRepository>()) }
    single { GetNotesUseCase(get<NoteRepository>()) }
    single { InsertNoteUseCase(get<NoteRepository>()) }
    single { UpdateNoteUseCase(get<NoteRepository>()) }
    single { WeatherUseCase(get<WeatherRepository>()) }

    //ViewModel
    viewModel {
        LoginViewModel(
            get<GetPasswordUseCase>(),
            get<PreferenceDataStore>()
        )
    }
    viewModel {
        SettingPasswordViewModel(
            get<InsertPasswordUseCase>(),
            get<PreferenceDataStore>()
        )
    }
    viewModel {
        CheckListViewModel(
            get<InsertNoteUseCase>(),
            get<GetNoteUseCase>(),
            get<UpdateNoteUseCase>(),
            get<SavedStateHandle>()
        )
    }
    viewModel {
        DrawViewModel(
            get<InsertNoteUseCase>(),
            get<GetNoteUseCase>(),
            get<UpdateNoteUseCase>(),
            get<SavedStateHandle>()
        )
    }
    viewModel {
        HomeViewModel(
            get<GetNotesUseCase>(),
            get<DeleteNoteUseCase>(),
            get<GetNoteUseCase>(),
            get<ILocationProvider>(),
            get<WeatherUseCase>()
        )
    }
    viewModel {
        NoteViewModel(
            get<InsertNoteUseCase>(),
            get<GetNoteUseCase>(),
            get<UpdateNoteUseCase>(),
            get<SavedStateHandle>()
        )
    }
    viewModel {
        SalaryViewModel(
            get<InsertNoteUseCase>(),
            get<GetNoteUseCase>(),
            get<UpdateNoteUseCase>(),
            get<SavedStateHandle>()
        )
    }
}