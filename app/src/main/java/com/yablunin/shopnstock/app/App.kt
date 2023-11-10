package com.yablunin.shopnstock.app

import android.app.Application
import com.yablunin.shopnstock.di.appModule
import com.yablunin.shopnstock.di.dataModule
import com.yablunin.shopnstock.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}