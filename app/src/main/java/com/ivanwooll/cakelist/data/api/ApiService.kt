package com.ivanwooll.cakelist.data.api

import com.ivanwooll.cakelist.data.api.models.ApiCake
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("waracle_cake-android-client")
    fun fetchCakes(): Call<List<ApiCake>>
}