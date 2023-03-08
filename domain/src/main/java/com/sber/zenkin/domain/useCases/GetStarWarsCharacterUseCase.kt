package com.sber.zenkin.domain.useCases

import androidx.paging.PagingData
import androidx.paging.map
import com.sber.zenkin.domain.StarWarsRepository
import com.sber.zenkin.domain.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class GetStarWarsCharacterUseCase(
    private val starWarsRepository: StarWarsRepository
) {

    suspend fun getCharacters(searchName: String): Flow<PagingData<Character>> {
        val deferredCharactersFromApi = coroutineScope {
            async(Dispatchers.IO) {
                starWarsRepository.getCharactersFromApi(searchName)
            }
        }
        val deferredCharactersFromDb = coroutineScope {
            async(Dispatchers.IO) {
                getCharactersFromDb(searchName)
            }
        }

        val pagingNetworkCharactersFlow = deferredCharactersFromApi.await()
        val characterListDatabase = deferredCharactersFromDb.await()

        return pagingNetworkCharactersFlow.map { pagingData ->
            pagingData.map { character ->
                markFavoriteCharacter(
                    character, characterListDatabase
                )
            }
        }.catch { }
    }

    private fun markFavoriteCharacter(
        charactersFromData: Character, charactersFromDb: List<Character>
    ): Character {
        charactersFromData.favorite = charactersFromDb.contains(charactersFromData)
        return charactersFromData
    }

    fun getCharactersFromDb(searchName: String): List<Character> {
        return starWarsRepository.getCharactersFromDatabase(searchName)
    }
}