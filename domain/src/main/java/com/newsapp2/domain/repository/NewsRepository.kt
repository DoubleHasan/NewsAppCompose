package com.newsapp2.domain.repository

import com.newsapp2.domain.model.News

interface NewsRepository {
    suspend fun getNews(query: String): List<News>
}