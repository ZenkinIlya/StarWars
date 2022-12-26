package com.sber.zenkin.data.di

import com.sber.zenkin.data.cache.CharactersCache
import com.sber.zenkin.data.db.dao.AppDatabase
import com.sber.zenkin.data.network.CharacterPagingSource
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
        charactersCache: CharactersCache
    ): StarWarsCharacterRepository {
        return StarWarsCharacterRepository(
            starWarsApiService,
            appDatabase,
            charactersCache
        )
    }

}