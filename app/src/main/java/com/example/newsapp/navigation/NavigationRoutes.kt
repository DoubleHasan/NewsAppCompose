package com.example.newsapp.navigation

import com.newsapp2.domain.model.News
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
sealed interface NavigationRoutes {
    @Serializable
    object HomeRoute : NavigationRoutes

    @Serializable
    object SavedRoute : NavigationRoutes

    @Serializable
    object DropDownRoute : NavigationRoutes

    @Serializable
    data class DetailRoute(
        val news: News
    ) : NavigationRoutes
}

val customArgTypes = mapOf(
    typeOf<News>() to NewsNavType
)