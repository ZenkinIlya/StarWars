package com.sber.zenkin.domain.common

sealed class Result<T> {
    class Success<T>(val data: T): Result<T>()
    class Error<T>(val t: Throwable): Result<T>()
    class Loading<T>: Result<T>()
}