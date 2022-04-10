package com.example.dictionaryapp.core.utils

typealias SimpleResponse = Response<Unit>

sealed class Response<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Response<T>(data)
    class Error<T>(data: T? = null, message: String) : Response<T>(data, message)
    class Loading<T>(data: T? = null) : Response<T>(data)
}

const val HTTP_ERROR = "Oops, something went wrong!"
const val IO_ERROR = "Couldn't reach server, please check your network connection."