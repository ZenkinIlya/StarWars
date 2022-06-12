package com.sber.zenkin.starwars.presentation.ui.search

import com.sber.zenkin.domain.model.Character

interface CharacterClickHandler {

    fun onClickFavorite(character: Character)
    fun onClickCharacter(character: Character)
}