package com.sber.zenkin.data.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sber.zenkin.data.db.model.CharacterDb

@Database(
    version = 1,
    entities = [
        CharacterDb::class
    ]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
}