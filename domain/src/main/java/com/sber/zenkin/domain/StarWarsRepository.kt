package com.sber.zenkin.domain

import androidx.paging.PagingData
import com.sber.zenkin.domain.common.Resources
import com.sber.zenkin.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface StarWarsRepository {
    suspend fun getCharactersFromApi(searchName: String): Flow<PagingData<Character>>
    suspend fun getCharactersFromDao(searchName: String = ""): List<Character>
    suspend fun saveCharacterInDao(character: Character)
    suspend fun deleteCharacterFromDao(character: Character)
    suspend fun deleteCharactersInDao()
    suspend fun saveCharactersInCache(listCharacters: PagingData<Character>)
    suspend fun getCharactersFromCache(): List<Character>
}