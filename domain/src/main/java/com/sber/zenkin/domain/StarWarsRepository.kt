package com.sber.zenkin.domain

import androidx.paging.PagingData
import com.sber.zenkin.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface StarWarsRepository {
    suspend fun getCharactersFromApi(searchName: String): Flow<PagingData<Character>>
    suspend fun getCharactersFromDb(searchName: String = ""): List<Character>
    suspend fun saveCharacterInDb(character: Character)
    suspend fun deleteCharacterFromDb(character: Character)
    suspend fun deleteCharactersInDb()
    suspend fun saveCharacterInCache(character: Character)
    suspend fun getCharactersFromCache(): List<Character>
}