package com.ivanwooll.cakelist.data.dagger

import com.ivanwooll.cakelist.MainViewModel
import com.ivanwooll.cakelist.data.CakeRepository
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun mainViewModel(cakeRepository: CakeRepository): MainViewModel {
        return MainViewModel.ViewModelFactory(cakeRepository).create(MainViewModel::class.java)
    }
}