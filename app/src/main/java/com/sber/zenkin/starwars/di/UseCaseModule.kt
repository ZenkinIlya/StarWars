package com.sber.zenkin.starwars.di

import com.sber.zenkin.data.repository.StarWarsCharacterRepository
import com.sber.zenkin.domain.useCases.GetStarWarsCharacterUseCase
import com.sber.zenkin.domain.useCases.SaveStarWarsCharacterUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetStarWarsCharacterUseCase(starWarsCharacterRepository: StarWarsCharacterRepository): GetStarWarsCharacterUseCase {
        return GetStarWarsCharacterUseCase(starWarsCharacterRepository)
    }

    @Provides
    @Singleton
    fun provideSaveStarWarsCharacterUseCase(starWarsCharacterRepository: StarWarsCharacterRepository): SaveStarWarsCharacterUseCase {
        return SaveStarWarsCharacterUseCase(starWarsCharacterRepository)
    }

}