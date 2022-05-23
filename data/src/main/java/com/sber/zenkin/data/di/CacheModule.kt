package com.sber.zenkin.data.di

import android.content.Context
import android.content.SharedPreferences
import com.sber.zenkin.data.cache.CharactersCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Provides
    @Singleton
    fun provideCharactersCache(sharedPreferences: SharedPreferences): CharactersCache{
        return CharactersCache(sharedPreferences)
    }

}