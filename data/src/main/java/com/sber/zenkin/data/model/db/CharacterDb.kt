package com.sber.zenkin.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sber.zenkin.domain.model.Character

@Entity(
    tableName = "characters",
)
data class CharacterDb(

    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "height")
    val height: String,
    @ColumnInfo(name = "mass")
    val mass: String,
    @ColumnInfo(name = "hairColor")
    val hairColor: String,
    @ColumnInfo(name = "skinColor")
    val skinColor: String,
    @ColumnInfo(name = "eyeColor")
    val eyeColor: String,
    @ColumnInfo(name = "birthYear")
    val birthYear: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "homeWorld")
    val homeWorld: String,
)