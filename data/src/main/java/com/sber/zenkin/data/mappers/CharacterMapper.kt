package com.sber.zenkin.data.mappers

import com.sber.zenkin.data.model.db.CharacterDb
import com.sber.zenkin.data.model.network.CharacterApi
import com.sber.zenkin.domain.model.Character

class CharacterMapper {

    fun toDomainCharacter(characterDb: CharacterDb) =
        Character(
            name = characterDb.name,
            height = characterDb.height,
            mass = characterDb.mass,
            hairColor = characterDb.hairColor,
            skinColor = characterDb.skinColor,
            eyeColor = characterDb.eyeColor,
            birthYear = characterDb.birthYear,
            gender = characterDb.gender,
            homeWorld = characterDb.homeWorld,
            favorite = true
        )

    fun fromDomainCharacter(character: Character) =
        CharacterDb(
            name = character.name,
            height = character.height,
            mass = character.mass,
            hairColor = character.hairColor,
            skinColor = character.skinColor,
            eyeColor = character.eyeColor,
            birthYear = character.birthYear,
            gender = character.gender,
            homeWorld = character.homeWorld
        )

    fun toDomainCharacter(characterApi: CharacterApi) =
        Character(
            name = characterApi.name,
            height = characterApi.height,
            mass = characterApi.mass,
            hairColor = characterApi.hair_color,
            skinColor = characterApi.skin_color,
            eyeColor = characterApi.eye_color,
            birthYear = characterApi.birth_year,
            gender = characterApi.gender,
            homeWorld = characterApi.homeworld,
            favorite = false
        )

}