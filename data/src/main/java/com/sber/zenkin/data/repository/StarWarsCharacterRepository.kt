package com.sber.zenkin.data.repository

import android.util.Log
import com.sber.zenkin.data.cache.CharactersCache
import com.sber.zenkin.data.dao.AppDatabase
import com.sber.zenkin.data.mappers.CharacterMapper
import com.sber.zenkin.data.model.network.CharacterApi
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.data.network.StarWarsApiService
import com.sber.zenkin.domain.StarWarsRepository
import kotlinx.coroutines.flow.emptyFlow
import timber.log.Timber
import javax.inject.Inject

class StarWarsCharacterRepository @Inject constructor(
    private val starWarsApiService: StarWarsApiService,
    private val appDatabase: AppDatabase,
    private val characterMapper: CharacterMapper,
    private val charactersCache: CharactersCache
) : StarWarsRepository {

    override suspend fun getCharactersFromApi(searchName: String): List<Character> {
        val response = if (searchName.isBlank()) {
            starWarsApiService.getCharacters(null)
        } else {
            starWarsApiService.getCharacters(searchName)
        }
        return if (response.isSuccessful) {
            val characterApis = response.body()?.results ?: emptyList()
            val characters = characterApis.map { characterApi ->
                characterMapper.toDomainCharacter(characterApi)
            }
            Timber.d(characters.toString())
            characters
        } else {
            val errorBody = response.errorBody().toString()
            Timber.d(errorBody)
            emptyList()
        }
    }

    override suspend fun getCharactersFromDao(searchName: String): List<Character> {
        return appDatabase.charactersDao().getCharacters()
            .map { characterDb -> characterMapper.toDomainCharacter(characterDb) }
    }

    override suspend fun saveCharacterInDao(character: Character) {
        appDatabase.charactersDao().insertCharacter(characterMapper.fromDomainCharacter(character))
    }

    override suspend fun deleteCharacterFromDao(character: Character) {
        appDatabase.charactersDao().deleteCharacter(characterMapper.fromDomainCharacter(character))
    }

    override suspend fun deleteCharactersInDao() {
        appDatabase.charactersDao().deleteAllCharacters()
    }

    override suspend fun saveCharactersInCache(listCharacters: List<Character>) {
        charactersCache.putCharacters(listCharacters)
    }

    override suspend fun getCharactersFromCache(): List<Character> {
        return charactersCache.getCharacters()
    }
}