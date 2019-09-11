package com.ivanwooll.cakelist

import android.app.Application
import com.ivanwooll.cakelist.data.dagger.AppComponent
import com.ivanwooll.cakelist.data.dagger.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent =
            DaggerAppComponent
                .builder()
                .build()
    }
}