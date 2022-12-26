package com.sber.zenkin.domain.common

sealed class Resources<T>(val data: T, val message: String? = null) {
    class Success<T>(data: T) : Resources<T>(data)
    class Error<T>(data: T, message: String) : Resources<T>(data, message)
    class Loading<T>(data: T) : Resources<T>(data)
}