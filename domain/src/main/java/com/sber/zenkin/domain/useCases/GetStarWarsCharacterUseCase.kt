package com.sber.zenkin.domain.useCases

import androidx.paging.PagingData
import androidx.paging.map
import com.sber.zenkin.domain.StarWarsRepository
import com.sber.zenkin.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class GetStarWarsCharacterUseCase(
    private val starWarsRepository: StarWarsRepository
) {

    suspend fun getCharacters(searchName: String = "a"): Flow<PagingData<Character>> {
        return starWarsRepository.getCharactersFromApi(searchName)
            .map { pagingData ->
                pagingData.map { character ->
                    markFavoriteCharacter(
                        character,
                        searchName
                    )
                }
            }
            .onEach { pagingData -> pagingData.map { starWarsRepository.saveCharacterInCache(it) } }
            .catch { }
    }

    private suspend fun markFavoriteCharacter(
        charactersFromData: Character,
        searchName: String
    ): Character {
        val charactersFromDb = starWarsRepository.getCharactersFromDb(searchName)
        charactersFromData.favorite = charactersFromDb.contains(charactersFromData)
        return charactersFromData
    }

    suspend fun getCharactersFromDb(searchName: String): List<Character> {
        return starWarsRepository.getCharactersFromDb(searchName)
    }
}