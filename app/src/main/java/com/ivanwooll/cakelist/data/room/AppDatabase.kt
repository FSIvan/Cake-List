package com.ivanwooll.cakelist.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Cake::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cakeDao(): CakeDao
}