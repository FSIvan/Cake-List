package com.ivanwooll.cakelist.extensions

import com.ivanwooll.cakelist.data.ApiResponse
import com.ivanwooll.cakelist.data.Errors
import retrofit2.Call
import retrofit2.Response

/**
 * Executes the call and returns ApiResponse. If the doOnSuccess is not null it is invoked with the body if this is also not null
 */
fun <T> Call<T>.executeAndUnwrap(doOnSuccess: ((T) -> Unit)? = null): ApiResponse<T> {
    return try {
        execute().apply {
            if (isSuccessful) {
                doOnSuccess?.let { doOnSuccess -> body()?.let { body -> doOnSuccess(body) } }
            }
        }.unwrap()
    } catch (e: Exception) {
        ApiResponse.Error(e.cause.toString())
    }
}

fun <T> Response<T>.unwrap(): ApiResponse<T> {
    return if (isSuccessful) {
        ApiResponse.Success(body())
    } else {
        ApiResponse.Error(Errors.DEFAULT_API)
    }
}