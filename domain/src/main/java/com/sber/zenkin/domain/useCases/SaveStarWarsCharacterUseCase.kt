package com.sber.zenkin.domain.useCases

import com.sber.zenkin.domain.StarWarsRepository
import com.sber.zenkin.domain.model.Character

class SaveStarWarsCharacterUseCase(
    private val starWarsRepository: StarWarsRepository
) {

    suspend fun addCharacterToFavorite(character: Character) {
        starWarsRepository.saveCharacterInDatabase(character)
    }

    suspend fun removeCharacterFromFavorite(character: Character) {
        starWarsRepository.deleteCharacterFromDatabase(character)
    }
}