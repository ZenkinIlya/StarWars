package com.sber.zenkin.domain.useCases

import com.sber.zenkin.domain.StarWarsRepository
import com.sber.zenkin.domain.model.Character
import java.net.UnknownHostException

class GetStarWarsCharacterUseCase(
    private val starWarsRepository: StarWarsRepository
) {

    suspend fun getCharacters(searchName: String = ""): List<Character> {
        return try {
            val characterFromApi = starWarsRepository.getCharactersFromApi(searchName)
            starWarsRepository.saveCharactersInCache(characterFromApi)
            markFavoriteCharacters(characterFromApi, searchName)
        } catch (e: UnknownHostException) {
            val charactersFromCache = starWarsRepository.getCharactersFromCache()
            markFavoriteCharacters(charactersFromCache, searchName)
        }
    }

    private suspend fun markFavoriteCharacters(
        listCharactersFromData: List<Character>,
        searchName: String
    ): List<Character> {
        val charactersFromDao = starWarsRepository.getCharactersFromDao(searchName)
        listCharactersFromData
            .forEach { character -> character.favorite = charactersFromDao.contains(character) }
        return listCharactersFromData
    }

    suspend fun addCharacterToFavorite(character: Character){
        starWarsRepository.saveCharacterInDao(character)
    }

    suspend fun removeCharacterFromFavorite(character: Character){
        starWarsRepository.deleteCharacterFromDao(character)
    }

    suspend fun deleteAllCharacter(){
        starWarsRepository.deleteCharactersInDao()
    }
}