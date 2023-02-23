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
import javax.inject.Inject

class StarWarsCharacterRepository @Inject constructor(
    private val starWarsApiService: StarWarsApiService,
    private val appDatabase: AppDatabase,
    private val charactersCache: CharactersCache
) : StarWarsRepository {

    override suspend fun getCharactersFromApi(searchName: String): Flow<PagingData<Character>> {
        return Pager(
            PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = true, prefetchDistance = 3),
            pagingSourceFactory = { CharacterPagingSource(starWarsApiService, searchName) }
        ).flow
            .catch { }
    }

    //DataBase
    override suspend fun getCharactersFromDb(searchName: String): List<Character> {
        return try {
            appDatabase.charactersDao().getCharacters()
                .map { it.toDomainCharacter() }
        } catch (e: Exception) {
            return emptyList()
        }
    }

    override suspend fun saveCharacterInDb(character: Character) {
        appDatabase.charactersDao().insertCharacter(character.fromDomainCharacter())
    }

    override suspend fun deleteCharacterFromDb(character: Character) {
        appDatabase.charactersDao().deleteCharacter(character.fromDomainCharacter())
    }

    override suspend fun deleteCharactersInDb() {
        appDatabase.charactersDao().deleteAllCharacters()
    }

    //Cache
    override suspend fun saveCharacterInCache(character: Character) {
        charactersCache.putCharacter(character)
    }

    override suspend fun getCharactersFromCache(): List<Character> {
        return try {
            charactersCache.getCharacters()
        } catch (e: Exception) {
            emptyList()
        }
    }
}