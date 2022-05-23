package com.sber.zenkin.starwars

import android.app.Application
import android.content.Context
import com.sber.zenkin.starwars.di.*
import timber.log.Timber

class App : Application() {

    lateinit var componentManager: ComponentManager

    override fun onCreate() {
        super.onCreate()
        initComponentManager()
        initLogging()
    }

    private fun initComponentManager() {
        componentManager = ComponentManager(this)
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}

val Context.componentManager: ComponentManager
    get() = when (this) {
        is App -> componentManager
        else -> this.applicationContext.componentManager
    }