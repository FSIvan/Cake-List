package com.ivanwooll.cakelist.data.dagger

import com.ivanwooll.cakelist.data.CakeRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DatabaseModule::class, ContextModule::class])
interface AppComponent {
    fun inject(cakeRepository: CakeRepository)

}