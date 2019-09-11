package com.ivanwooll.cakelist.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CakeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // ensures no duplicates will be present in the table
    fun insert(cakes: List<Cake>)

    @Query("DELETE FROM cakes")
    fun clearCakes()

    @Query("SELECT * FROM cakes")
    fun allLiveData(): LiveData<List<Cake>>


}