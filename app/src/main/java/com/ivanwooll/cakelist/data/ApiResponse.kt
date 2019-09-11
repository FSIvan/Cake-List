package com.ivanwooll.cakelist.data

sealed class ApiResponse<T> {
    data class Success<T>(val body: T?) : ApiResponse<T>()
    data class Error<T>(val message: String) : ApiResponse<T>()
}