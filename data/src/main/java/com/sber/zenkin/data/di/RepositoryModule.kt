package com.sber.zenkin.data.di

import com.sber.zenkin.data.cache.CharactersCache
import com.sber.zenkin.data.dao.AppDatabase
import com.sber.zenkin.data.mappers.CharacterMapper
import com.sber.zenkin.data.network.StarWarsApiService
import com.sber.zenkin.data.repository.StarWarsCharacterRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideStarWarsCharacterRepository(
        starWarsApiService: StarWarsApiService,
        appDatabase: AppDatabase,
        characterMapper: CharacterMapper,
        charactersCache: CharactersCache
    ): StarWarsCharacterRepository {
        return StarWarsCharacterRepository(
            starWarsApiService,
            appDatabase,
            characterMapper,
            charactersCache
        )
    }

}