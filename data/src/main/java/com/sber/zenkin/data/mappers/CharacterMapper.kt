package com.sber.zenkin.data.mappers

import com.sber.zenkin.data.db.model.CharacterDb
import com.sber.zenkin.data.network.model.CharacterApi
import com.sber.zenkin.domain.model.Character

fun CharacterDb.toDomainCharacter() =
    Character(
        name = name,
        height = height,
        mass = mass,
        hairColor = hairColor,
        skinColor = skinColor,
        eyeColor = eyeColor,
        birthYear = birthYear,
        gender = gender,
        homeWorld = homeWorld,
        favorite = true
    )

fun Character.fromDomainCharacter() =
    CharacterDb(
        name = name,
        height = height,
        mass = mass,
        hairColor = hairColor,
        skinColor = skinColor,
        eyeColor = eyeColor,
        birthYear = birthYear,
        gender = gender,
        homeWorld = homeWorld
    )

internal fun CharacterApi.toDomainCharacter() =
    Character(
        name = name,
        height = height,
        mass = mass,
        hairColor = hair_color,
        skinColor = skin_color,
        eyeColor = eye_color,
        birthYear = birth_year,
        gender = gender,
        homeWorld = homeworld,
        favorite = false
    )