package com.sber.zenkin.data.dao

import androidx.room.*
import com.sber.zenkin.data.model.db.CharacterDb

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(characterDb: CharacterDb)

    @Delete
    suspend fun deleteCharacter(characterDb: CharacterDb)

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()

    @Query("SELECT * FROM characters")
    suspend fun getCharacters(): List<CharacterDb>

}
