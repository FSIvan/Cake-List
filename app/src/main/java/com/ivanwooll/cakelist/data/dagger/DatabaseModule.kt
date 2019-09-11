package com.ivanwooll.cakelist.data.dagger

import android.content.Context
import androidx.room.Room
import com.ivanwooll.cakelist.data.room.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun database(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "room_database.db")
            .build()
    }
}