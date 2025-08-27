package com.roys.wolvnotekmp.di

import androidx.room.RoomDatabase
import com.roys.wolvnotekmp.domain.repository.ILocationProvider
import com.roys.wolvnotekmp.common.IosPreferencesDataStore
import com.roys.wolvnotekmp.common.PreferenceDataStore
import com.roys.wolvnotekmp.data.database.AppDatabase
import com.roys.wolvnotekmp.data.database.DatabaseFactoryIos
import com.roys.wolvnotekmp.data.repository.LocationRepositoryImpl
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<RoomDatabase.Builder<AppDatabase>> { DatabaseFactoryIos() }
        single<PreferenceDataStore> { IosPreferencesDataStore() }
        single<ILocationProvider> { LocationRepositoryImpl() }
        single { Darwin.create() }
    }