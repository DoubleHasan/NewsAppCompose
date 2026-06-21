package com.newsapp2.domain.usecase

import com.newsapp2.domain.model.News
import com.newsapp2.domain.repository.NewsRepository
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(query: String): List<News> {
        val newsList = repository.getNews(query )
        return newsList
    }
}