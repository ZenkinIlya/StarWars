package com.sber.zenkin.starwars.di

import com.sber.zenkin.data.di.*
import com.sber.zenkin.starwars.presentation.ui.search.SearchedCharacterFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    AppModule::class,
    UseCaseModule::class,
    CacheModule::class,
    MapperModule::class,
    RepositoryModule::class,
    RetrofitModule::class,
    RoomModule::class
    ])
@Singleton
interface AppComponent {

    fun inject(searchedCharacterFragment: SearchedCharacterFragment)
}