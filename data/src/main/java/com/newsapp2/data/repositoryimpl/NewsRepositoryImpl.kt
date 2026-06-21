package com.newsapp2.data.repositoryimpl

import com.newsapp2.data.dto.NewsDto
import com.newsapp2.data.mapper.toNewsList
import com.newsapp2.domain.model.News
import com.newsapp2.domain.repository.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val client: HttpClient) : NewsRepository {
    override suspend fun getNews(query : String): List<News> {

        val apiKey = "9424855db1824f37ac3dd48103fa829d"

        val data =
            client.get("https://newsapi.org/v2/everything?q=$query&apiKey=$apiKey")
                .body<NewsDto>()

        return data.toNewsList()

    }


}

