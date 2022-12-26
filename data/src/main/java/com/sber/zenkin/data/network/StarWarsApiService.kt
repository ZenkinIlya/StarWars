package com.sber.zenkin.data.network

import com.sber.zenkin.data.network.model.CharacterApi
import com.sber.zenkin.data.network.model.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApiService {

    /**
     * https://swapi.dev/api/people?search=Luke&page=2
     *
     * https://swapi.dev/api/people?search=Luke
     *
     * https://swapi.dev/api/people
     *
     * */
    @GET("people")
    suspend fun getCharacters(
        @Query("search") search: String? = null,
        @Query("page") page: Int? = null
    ): Response<ResponseBody>

    /**
     * https://swapi.dev/api/people/1
     * */
    @GET("people/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): Response<CharacterApi>
}