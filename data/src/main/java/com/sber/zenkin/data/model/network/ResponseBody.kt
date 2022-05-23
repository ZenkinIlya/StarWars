package com.sber.zenkin.data.model.network

data class ResponseBody(
    val count: Int, // 1
    val next: Any, // null
    val previous: Any, // null
    val results: List<CharacterApi>
)