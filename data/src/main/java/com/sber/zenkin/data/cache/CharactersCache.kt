package com.sber.zenkin.data.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sber.zenkin.domain.model.Character
import java.lang.reflect.Type


class CharactersCache(private val sharedPreferences: SharedPreferences) {

    suspend fun clearCache() {
        val gson = Gson()
        val jsonEmptyCharacters = gson.toJson(emptyList<Character>())
        sharedPreferences.edit()
            .putString(CACHE_CHARACTERS, jsonEmptyCharacters)
            .apply()
    }

    suspend fun putCharacter(character: Character) {
        val gson = Gson()
        val jsonCharacters = gson.toJson(character)
        sharedPreferences.edit()
            .putString(CACHE_CHARACTERS, jsonCharacters)
            .apply()
    }

    suspend fun getCharacters(): List<Character> {
        val gson = Gson()
        val json = sharedPreferences.getString(CACHE_CHARACTERS, "")
        val listType: Type = object : TypeToken<List<Character>>() {}.type
        return gson.fromJson(json, listType)
    }

    companion object {
        const val CACHE_CHARACTERS = "cacheCharacters"
    }
}