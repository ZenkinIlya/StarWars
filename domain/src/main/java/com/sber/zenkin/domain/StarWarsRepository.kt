package com.sber.zenkin.domain

import androidx.paging.PagingData
import com.sber.zenkin.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface StarWarsRepository {
    suspend fun getCharactersFromApi(searchName: String): Flow<PagingData<Character>>
    fun getCharactersFromDatabase(searchName: String): List<Character>
    suspend fun saveCharacterInDatabase(character: Character)
    suspend fun deleteCharacterFromDatabase(character: Character)
    suspend fun deleteAllCharactersFromDatabase()
    suspend fun saveCharacterInCache(character: Character)
    suspend fun getCharactersFromCache(): List<Character>
}