package com.roys.wolvnotekmp.di

import androidx.room.RoomDatabase
import com.roys.wolvnotekmp.domain.repository.ILocationProvider
import com.roys.wolvnotekmp.common.JvmPreferenceDataStore
import com.roys.wolvnotekmp.common.PreferenceDataStore
import com.roys.wolvnotekmp.data.database.AppDatabase
import com.roys.wolvnotekmp.data.database.DatabaseFactoryJvm
import com.roys.wolvnotekmp.data.repository.LocationRepositoryImpl
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<RoomDatabase.Builder<AppDatabase>> { DatabaseFactoryJvm() }
        single<PreferenceDataStore> { JvmPreferenceDataStore() }
        single<ILocationProvider> { LocationRepositoryImpl() }
        single { OkHttp.create() }
    }