package com.sber.zenkin.domain.model

data class Character(
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val homeWorld: String,

    var favorite: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Character

        if (name != other.name) return false
        if (height != other.height) return false
        if (mass != other.mass) return false
        if (hairColor != other.hairColor) return false
        if (skinColor != other.skinColor) return false
        if (eyeColor != other.eyeColor) return false
        if (birthYear != other.birthYear) return false
        if (gender != other.gender) return false
        if (homeWorld != other.homeWorld) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + height.hashCode()
        result = 31 * result + mass.hashCode()
        result = 31 * result + hairColor.hashCode()
        result = 31 * result + skinColor.hashCode()
        result = 31 * result + eyeColor.hashCode()
        result = 31 * result + birthYear.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + homeWorld.hashCode()
        return result
    }
}


