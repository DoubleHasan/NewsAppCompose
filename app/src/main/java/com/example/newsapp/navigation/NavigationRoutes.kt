package com.example.newsapp.navigation

import com.newsapp2.domain.model.News
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
object HomeRoute

@Serializable
object SavedRoute

@Serializable
object DropDownRoute

@Serializable
data class DetailRoute(
    val news : News
)

val customArgTypes = mapOf(
    typeOf<News>() to NewsNavType
)