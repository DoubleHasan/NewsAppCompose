package com.newsapp2.data.mapper

import com.newsapp2.data.room.RoomEntity
import com.newsapp2.domain.model.News

fun News.toEntity(): RoomEntity {
    return RoomEntity(
        url = this.url,
        content = this.content,
        publishedAt = this.publishedAt,
        author = this.author,
        title = this.title,
        urlToImage = this.urlToImage,
        description = this.description
    )
}

fun RoomEntity.toDomain(): News {
    return News(
        url = this.url,
        content = this.content,
        urlToImage = this.urlToImage,
        author = this.author,
        title = this.title,
        publishedAt = this.publishedAt,
        description = this.description
    )
}