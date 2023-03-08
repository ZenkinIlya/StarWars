package com.sber.zenkin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sber.zenkin.data.cache.CharactersCache
import com.sber.zenkin.data.db.dao.AppDatabase
import com.sber.zenkin.data.mappers.fromDomainCharacter
import com.sber.zenkin.data.mappers.toDomainCharacter
import com.sber.zenkin.data.network.CharacterPagingSource
import com.sber.zenkin.data.network.StarWarsApiService
import com.sber.zenkin.domain.StarWarsRepository
import com.sber.zenkin.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import timber.log.Timber
import javax.inject.Inject

class StarWarsCharacterRepository @Inject constructor(
    private val starWarsApiService: StarWarsApiService,
    private val appDatabase: AppDatabase,
    private val charactersCache: CharactersCache
) : StarWarsRepository {

    override suspend fun getCharactersFromApi(searchName: String): Flow<PagingData<Character>> {
        return Pager(
            PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 3
            ),
            pagingSourceFactory = { CharacterPagingSource(starWarsApiService, searchName) }
        ).flow
            .catch { }
    }

    //DataBase
    override fun getCharactersFromDatabase(searchName: String): List<Character> {
        return if (searchName.isEmpty()) {
            appDatabase.charactersDao().getCharacters()
        } else {
            appDatabase.charactersDao().getCharactersByName(searchName)
        }
            .map { it.fromDomainCharacter() }
            .onEach { Timber.d("characters from database: $it") }
    }

    override suspend fun saveCharacterInDatabase(character: Character) {
        Timber.i("save character in database: ${character.name}")
        appDatabase.charactersDao().insertCharacter(character.toDomainCharacter())
    }

    override suspend fun deleteCharacterFromDatabase(character: Character) {
        Timber.i("delete character from database: ${character.name}")
        appDatabase.charactersDao().deleteCharacter(character.toDomainCharacter())
    }

    override suspend fun deleteAllCharactersFromDatabase() {
        Timber.i("delete all characters from database")
        appDatabase.charactersDao().deleteAllCharacters()
    }

    //Cache
    override suspend fun saveCharacterInCache(character: Character) {
        Timber.i("save character in cache: $character")
        charactersCache.putCharacter(character)
    }

    override suspend fun getCharactersFromCache(): List<Character> {
        val characters = charactersCache.getCharacters()
        Timber.i("characters from cache: $characters")
        return characters
    }
}