package com.sber.zenkin.data.model.network

data class CharacterApi(
    val birth_year: String, // 19BBY - Before the Battle of Yavin or After the Battle of Yavin
    val created: String, // 2014-12-09T13:50:51.644000Z
    val edited: String, // 2014-12-20T21:17:56.891000Z
    val eye_color: String, // blue
    val films: List<String>,
    val gender: String, // male
    val hair_color: String, // blond
    val height: String, // 172
    val homeworld: String, // https://swapi.dev/api/planets/1/
    val mass: String, // 77
    val name: String, // Luke Skywalker
    val skin_color: String, // fair
    val species: List<Any>,
    val starships: List<String>,
    val url: String, // https://swapi.dev/api/people/1/
    val vehicles: List<String>
)