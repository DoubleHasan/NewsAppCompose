package com.example.newsapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavController.safeNavigate(
    route: Any,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    val currentRouteName = currentDestination?.route
    val targetRouteName = route::class.qualifiedName
    if (targetRouteName != currentRouteName) {
        navigate(route, builder)
    }
}