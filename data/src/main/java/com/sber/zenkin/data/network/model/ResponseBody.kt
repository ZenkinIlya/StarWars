package com.sber.zenkin.data.network.model

data class ResponseBody(
    val count: Int, // 1
    val next: String?, // null, "https://swapi.dev/api/people/?search=...&page=3"
    val previous: String?, // null, "https://swapi.dev/api/people/?search=...&page=2"
    val results: List<CharacterApi>
) {
    override fun toString(): String {
        return "ResponseBody(count=$count, next='$next', previous='$previous', results=[...])"
    }
}