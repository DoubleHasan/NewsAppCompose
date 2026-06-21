package com.newsapp2.data.mapper

import com.newsapp2.data.dto.Article
import com.newsapp2.data.dto.NewsDto
import com.newsapp2.domain.model.News

fun Article?.toNews(): News {
    return News(
        author = this?.author.orEmpty(),
        content = this?.content.orEmpty(),
        description = this?.description.orEmpty(),
        publishedAt = this?.publishedAt.orEmpty(),
        title = this?.title.orEmpty(),
        url = this?.url.orEmpty(),
        urlToImage = this?.urlToImage.orEmpty(),
    )
}

fun NewsDto.toNewsList(): List<News> {
    return this.articles?.map { it.toNews() } ?: emptyList()
}