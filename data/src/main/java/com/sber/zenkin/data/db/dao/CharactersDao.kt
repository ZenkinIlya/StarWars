package com.sber.zenkin.data.db.dao

import androidx.room.*
import com.sber.zenkin.data.db.model.CharacterDb

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(characterDb: CharacterDb)

    @Delete
    suspend fun deleteCharacter(characterDb: CharacterDb)

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()

    @Query("SELECT * FROM characters")
    fun getCharacters(): List<CharacterDb>

    @Query("SELECT * FROM characters WHERE name = :searchName")
    fun getCharactersByName(searchName: String): List<CharacterDb>

}
