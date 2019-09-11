package com.ivanwooll.cakelist.data

import androidx.lifecycle.LiveData
import com.ivanwooll.cakelist.App
import com.ivanwooll.cakelist.data.api.ApiService
import com.ivanwooll.cakelist.data.room.AppDatabase
import com.ivanwooll.cakelist.data.room.Cake
import com.ivanwooll.cakelist.extensions.executeAndUnwrap
import javax.inject.Inject

class CakeRepository {

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var database: AppDatabase

    init {
        App.appComponent.inject(this)
    }

    fun clearCakesTable() = database.cakeDao().clearCakes() // clear the existing table

    fun fetchCakes() = apiService.fetchCakes().executeAndUnwrap { apiCakeList ->
        // this will only be called if api call completes successfully
        val cakes = apiCakeList.map { apiCake ->
            Cake(
                desc = apiCake.desc,
                image = apiCake.image,
                title = apiCake.title
            )
        }
        database.cakeDao().insert(cakes)
    }

    fun cakesLiveData(): LiveData<List<Cake>> = database.cakeDao().allLiveData()
}