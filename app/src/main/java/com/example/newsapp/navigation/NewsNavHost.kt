package com.example.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.newsapp2.feature.home.HomeScreen
import com.newsapp2.feature.detail.DetailScreen
import com.newsapp2.feature.dropdown.DropDownScreen
import com.newsapp2.feature.saved.SavedScreen

@Composable
fun NewsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        composable<HomeRoute> {
            HomeScreen(onNewsClicked = { selectedNews ->
                navController.safeNavigate(DetailRoute(selectedNews))
            }, onLangCLick = {
                navController.safeNavigate(DropDownRoute)
            }, onBookmarkClicked = {
                navController.safeNavigate(SavedRoute)
            })
        }

        composable<DetailRoute>(
            typeMap = customArgTypes
        ) { backStackEntry ->

            val detailRoute: DetailRoute = backStackEntry.toRoute()
            val newsData = detailRoute.news

            DetailScreen(
                news = newsData,
                onBackClick = {
                    navigateToBackStack(navController)
                }
            )
        }

        composable<DropDownRoute> {
            DropDownScreen { navigateToBackStack(navController) }
        }

        composable<SavedRoute>() {
            SavedScreen({ navigateToBackStack(navController) }, onCardClicked = { selectedNews ->
                navController.safeNavigate(
                    DetailRoute(selectedNews)
                )
            })
        }
    }
}

fun navigateToBackStack(navController: NavController) {
    if (navController.previousBackStackEntry != null) {
        navController.popBackStack()
    }
}