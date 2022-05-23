package com.sber.zenkin.data.network

import com.sber.zenkin.data.model.network.CharacterApi
import com.sber.zenkin.data.model.network.ResponseBody
import com.sber.zenkin.domain.model.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApiService {

    /**
     * https://swapi.dev/api/people?search=Luke
     *
     * https://swapi.dev/api/people
     *
     * */
    @GET("people")
    suspend fun getCharacters(
        @Query("search") search: String? = null
    ): Response<ResponseBody>

    /**
     * https://swapi.dev/api/people/1
     * */
    @GET("people/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): Response<CharacterApi>
}