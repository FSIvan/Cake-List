package com.ivanwooll.cakelist.data.dagger

import com.ivanwooll.cakelist.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DatabaseModule::class, ContextModule::class, RepositoryModule::class, MainModule::class])
interface AppComponent {
//    fun inject(cakeRepository: CakeRepository)

    fun inject(mainActivity: MainActivity)

}