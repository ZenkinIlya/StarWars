package com.sber.zenkin.data.network.model

data class ResponseBody(
    val count: Int, // 1
    val next: String, // null
    val previous: String, // null
    val results: List<CharacterApi>
)