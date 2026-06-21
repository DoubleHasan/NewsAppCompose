package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.newsapp2.data.room.AppDatabase
import com.newsapp2.data.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "news_database"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(database: AppDatabase) : UserDao{
        return database.newsDao()
    }

}