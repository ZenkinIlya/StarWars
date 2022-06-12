package com.sber.zenkin.starwars.presentation.ui.search

import com.bumptech.glide.Glide
import com.sber.zenkin.domain.model.Character
import com.sber.zenkin.starwars.R
import com.sber.zenkin.starwars.databinding.FragmentCharacterBinding

interface CharacterViewHandler {

    fun bindView(): CharacterViewHandler
    fun bindFavoriteImageView(): CharacterViewHandler

    abstract class Abstract(
        private val binding: FragmentCharacterBinding,
        private val character: Character
    ) : CharacterViewHandler {

        override fun bindView(): CharacterViewHandler {
            with(binding) {
                nameCharacter.text = character.name
                heightCharacter.text = character.height
                massCharacter.text = character.mass
                hairColorCharacter.text = character.hairColor
                skinColorCharacter.text = character.skinColor
                eyeColorCharacter.text = character.eyeColor
                birthYearCharacter.text = character.birthYear
                genderCharacter.text = character.gender
                homeWorldCharacter.text = character.homeWorld
            }
            return this
        }

        override fun bindFavoriteImageView(): CharacterViewHandler {
            with(binding) {
                if (character.favorite) {
                    Glide.with(favoriteImageView.context)
                        .load(R.drawable.ic_baseline_favorite_24)
                        .placeholder(R.drawable.ic_baseline_favorite_border_24)
                        .error(R.drawable.ic_baseline_favorite_border_24)
                        .into(favoriteImageView)
                } else {
                    Glide.with(favoriteImageView.context).clear(favoriteImageView)
                    favoriteImageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
            return this
        }
    }

    class DefaultRepository(binding: FragmentCharacterBinding, character: Character) :
        Abstract(binding, character)
}