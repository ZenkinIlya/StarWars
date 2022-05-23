package com.sber.zenkin.domain

import com.sber.zenkin.domain.model.Character

interface StarWarsRepository {
    suspend fun getCharactersFromApi(searchName: String): List<Character>
    suspend fun getCharactersFromDao(searchName: String = ""): List<Character>
    suspend fun saveCharacterInDao(character: Character)
    suspend fun deleteCharacterFromDao(character: Character)
    suspend fun deleteCharactersInDao()
    suspend fun saveCharactersInCache(listCharacters: List<Character>)
    suspend fun getCharactersFromCache(): List<Character>
}