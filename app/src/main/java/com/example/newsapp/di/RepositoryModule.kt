package com.example.newsapp.di

import com.newsapp2.data.repositoryimpl.NewsRepositoryImpl
import com.newsapp2.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepo(repositoryImpl : NewsRepositoryImpl) : NewsRepository
}