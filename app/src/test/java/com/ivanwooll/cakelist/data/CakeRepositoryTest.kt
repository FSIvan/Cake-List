package com.ivanwooll.cakelist.data

import android.content.Context
import androidx.room.Room
import com.ivanwooll.cakelist.data.room.AppDatabase
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CakeRepositoryTest {

    lateinit var appDatabase: AppDatabase

    lateinit var context: Context

    @Before
    fun setup() {
        context = mock()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @Test
    fun test() {
    }

}