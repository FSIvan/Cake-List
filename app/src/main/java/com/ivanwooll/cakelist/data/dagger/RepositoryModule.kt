package com.ivanwooll.cakelist.data.dagger

import com.ivanwooll.cakelist.data.CakeRepository
import com.ivanwooll.cakelist.data.api.ApiService
import com.ivanwooll.cakelist.data.room.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun cakeRepository(apiService: ApiService, database: AppDatabase): CakeRepository =
        CakeRepository(apiService, database)
}