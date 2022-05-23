package com.sber.zenkin.data.di

import android.content.Context
import androidx.room.Room
import com.sber.zenkin.data.dao.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database.db")
            .build()
    }
}