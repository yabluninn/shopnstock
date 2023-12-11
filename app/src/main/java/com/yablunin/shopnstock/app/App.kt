package com.yablunin.shopnstock.app

import android.app.Application
import com.yablunin.shopnstock.di.AppComponent
import com.yablunin.shopnstock.di.DaggerAppComponent
import com.yablunin.shopnstock.di.DataModule


class App: Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

//        appComponent = DaggerAppComponent.create()
        appComponent = DaggerAppComponent.builder()
            .dataModule(DataModule(this))
            .build()
//        startKoin{
//            androidLogger(Level.DEBUG)
//            androidContext(this@App)
//            modules(listOf(appModule, domainModule, dataModule))
//        }
    }
}