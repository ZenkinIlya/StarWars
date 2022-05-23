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
            val markFavoriteCharacters = markFavoriteCharacters(characterFromApi, searchName)
            markFavoriteCharacters
        } catch (e: UnknownHostException) {
            val charactersFromCache = starWarsRepository.getCharactersFromCache()
            val markFavoriteCharactersFromCache = markFavoriteCharacters(charactersFromCache, searchName)
            markFavoriteCharactersFromCache
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
}