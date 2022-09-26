package com.example.galaxy.utils

sealed class DataStatus<T> {
    data class Success<T>(var data: T?) : DataStatus<T>()
    data class Loading<T>(var message: String = "") : DataStatus<T>()
    data class Error<T>(var message: String = "") : DataStatus<T>()
}