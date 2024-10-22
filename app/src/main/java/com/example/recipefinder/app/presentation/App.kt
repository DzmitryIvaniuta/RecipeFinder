package com.example.recipefinder.app.presentation

import android.app.Application
import com.example.recipefinder.app.presentation.di.AppComponent
import com.example.recipefinder.app.presentation.di.AppModule
import com.example.recipefinder.app.presentation.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}