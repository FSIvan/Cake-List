package com.ivanwooll.cakelist.data.room

import androidx.room.Entity


@Entity(tableName = "cakes",primaryKeys = ["desc", "image", "title"])
data class Cake(
    val desc: String,
    val image: String,
    val title: String
)