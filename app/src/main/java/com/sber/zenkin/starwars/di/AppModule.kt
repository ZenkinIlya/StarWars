package com.sber.zenkin.starwars.di

import android.content.Context
import android.content.SharedPreferences
import com.sber.zenkin.data.di.CacheModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(context: Context) {

    private var context: Context = context.applicationContext

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
    }

    companion object {
        const val APP_PREFS = "AppPrefs"
    }
}