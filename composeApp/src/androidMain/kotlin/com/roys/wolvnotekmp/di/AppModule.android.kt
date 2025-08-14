package com.roys.wolvnotekmp.di

import androidx.room.RoomDatabase
import com.roys.wolvnotekmp.common.AndroidPreferencesDataStore
import com.roys.wolvnotekmp.common.PreferenceDataStore
import com.roys.wolvnotekmp.data.database.AppDatabase
import com.roys.wolvnotekmp.data.database.DatabaseFactoryAndroid
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<RoomDatabase.Builder<AppDatabase>> { DatabaseFactoryAndroid(androidApplication()) }
        single<PreferenceDataStore> { AndroidPreferencesDataStore(androidApplication().applicationContext) }
    }