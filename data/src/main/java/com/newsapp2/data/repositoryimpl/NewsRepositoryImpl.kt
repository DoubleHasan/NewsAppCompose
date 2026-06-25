package com.newsapp2.data.repositoryimpl

import android.util.Log
import com.newsapp2.data.dto.NewsDto
import com.newsapp2.data.mapper.toNewsList
import com.newsapp2.domain.model.News
import com.newsapp2.domain.repository.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val client: HttpClient) : NewsRepository {
    override suspend fun getNews(query: String): List<News> {
        val data = client.get("https://newsapi.org/v2/everything") {
            url {
                parameters.append("q", query)
            }
        }.body<NewsDto>()

        return data.toNewsList()
    }
}