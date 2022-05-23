package com.sber.zenkin.data.di

import android.content.Context
import com.sber.zenkin.data.dao.AppDatabase
import com.sber.zenkin.data.mappers.CharacterMapper
import com.sber.zenkin.data.network.StarWarsApiService
import com.sber.zenkin.data.repository.StarWarsCharacterRepository
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModule {

    @Provides
    @Singleton
    fun provideCharacterMapper(): CharacterMapper {
        return CharacterMapper()
    }

}