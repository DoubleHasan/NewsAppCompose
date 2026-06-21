package com.newsapp2.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_news")
data class RoomEntity(
    @PrimaryKey
    val url : String,
    val content : String = "Finance",
    val title : String,
    val publishedAt : String,
    val author : String,
    val urlToImage : String,
    val description : String
)