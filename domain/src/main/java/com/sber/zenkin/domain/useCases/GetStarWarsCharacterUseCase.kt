package com.sber.zenkin.domain.useCases

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sber.zenkin.domain.StarWarsRepository
import com.sber.zenkin.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException

class GetStarWarsCharacterUseCase(
    private val starWarsRepository: StarWarsRepository
) {

    suspend fun getCharacters(searchName: String = "a"): Flow<PagingData<Character>> {
        return starWarsRepository.getCharactersFromApi(searchName)
            .map { pagingData ->
                pagingData.map { character -> markFavoriteCharacter(character, searchName) }
            }
//            starWarsRepository.saveCharactersInCache(characterFromApi)
/*        } catch (e: IOException) {
            val charactersFromCache = starWarsRepository.getCharactersFromCache()
            markFavoriteCharacter(charactersFromCache, searchName)
        }*/
    }

    private suspend fun markFavoriteCharacter(
        charactersFromData: Character,
        searchName: String
    ): Character {
        val charactersFromDao = starWarsRepository.getCharactersFromDao(searchName)
        charactersFromData.favorite = charactersFromDao.contains(charactersFromData)
        return charactersFromData
    }

    suspend fun addCharacterToFavorite(character: Character) {
        starWarsRepository.saveCharacterInDao(character)
    }

    suspend fun removeCharacterFromFavorite(character: Character) {
        starWarsRepository.deleteCharacterFromDao(character)
    }

    suspend fun deleteAllCharacter() {
        starWarsRepository.deleteCharactersInDao()
    }
}