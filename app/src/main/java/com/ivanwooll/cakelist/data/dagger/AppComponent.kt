package com.ivanwooll.cakelist.data.dagger

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface AppComponent {

}