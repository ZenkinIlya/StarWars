package com.sber.zenkin.data.network.model

data class ResponseBody(
    val count: Int, // 1
    val next: Int, // null
    val previous: Int, // null
    val results: List<CharacterApi>
)