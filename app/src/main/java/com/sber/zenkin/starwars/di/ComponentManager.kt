package com.sber.zenkin.starwars.di

import android.content.Context

class ComponentManager(private val context: Context) {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(context))
            .build()
    }
}
