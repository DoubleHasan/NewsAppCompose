package com.newsapp2.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newsapp2.domain.model.News
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: RoomEntity)

    @Delete
    suspend fun deleteNews(news: RoomEntity)

    @Query("SELECT * FROM saved_news")
    fun getAllSavedNews() : Flow<List<RoomEntity>>

    @Query("SELECT EXISTS (SELECT * FROM saved_news WHERE url = :url)")
    fun isNewsSaved(url: String) : Flow<Boolean>
}