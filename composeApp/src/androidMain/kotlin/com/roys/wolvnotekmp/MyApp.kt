package com.roys.wolvnotekmp

import android.app.Application
import com.roys.wolvnotekmp.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApp)
        }
    }
}