package com.example.galaxy.utils

sealed class Data<T>(val data: T? = null) {
    data class Success<T>(var _data: T) : Data<T>(_data)
    data class Loading<T>(var _data: T? = null) : Data<T>(_data)
    data class Error<T>(var message: String = "") : Data<T>(null)
}