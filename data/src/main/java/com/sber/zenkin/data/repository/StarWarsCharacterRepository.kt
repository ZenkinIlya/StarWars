package com.sber.zenkin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sber.zenkin.data.cache.CharactersCache
import com.sber.zenkin.data.db.dao.AppDatabase
import com.sber.zenkin.data.mappers.fromDomainCharacter
import com.sber.zenkin.data.mappers.toDomainCharacter
import com.sber.zenkin.data.network.CharacterPagingSource
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.data.network.StarWarsApiService
import com.sber.zenkin.domain.StarWarsRepository
import com.sber.zenkin.domain.common.Resources
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class StarWarsCharacterRepository @Inject constructor(
    private val starWarsApiService: StarWarsApiService,
    private val appDatabase: AppDatabase,
    private val charactersCache: CharactersCache
) : StarWarsRepository {

    override suspend fun getCharactersFromApi(searchName: String): Flow<PagingData<Character>> {
        return Pager(
            PagingConfig(pageSize = 1, initialLoadSize = 1),
            pagingSourceFactory = { CharacterPagingSource(starWarsApiService, searchName) }
        ).flow
            //TODO Catch exceptions from PagingSource
//            .catch { }
    }

    override suspend fun getCharactersFromDao(searchName: String): List<Character> {
        return try {
            appDatabase.charactersDao().getCharacters()
                .map { it.toDomainCharacter() }
        } catch (e: Exception) {
            return emptyList()
        }
    }

    override suspend fun saveCharacterInDao(character: Character) {
        appDatabase.charactersDao().insertCharacter(character.fromDomainCharacter())
    }

    override suspend fun deleteCharacterFromDao(character: Character) {
        appDatabase.charactersDao().deleteCharacter(character.fromDomainCharacter())
    }

    override suspend fun deleteCharactersInDao() {
        appDatabase.charactersDao().deleteAllCharacters()
    }

    override suspend fun saveCharactersInCache(listCharacters: PagingData<Character>) {
//        charactersCache.putCharacters(listCharacters)
    }

    override suspend fun getCharactersFromCache(): List<Character> {
        return try {
            charactersCache.getCharacters()
        } catch (e: Exception) {
            emptyList()
        }
    }
}