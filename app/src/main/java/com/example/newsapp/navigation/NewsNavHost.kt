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
        startDestination = NavigationRoutes.HomeRoute,
        modifier = modifier
    ) {
        composable<NavigationRoutes.HomeRoute> {
            HomeScreen(onNewsClicked = { selectedNews ->
                navController.safeNavigate(NavigationRoutes.DetailRoute(selectedNews))
            }, onLangCLick = {
                navController.safeNavigate(NavigationRoutes.DropDownRoute)
            }, onBookmarkClicked = {
                navController.safeNavigate(NavigationRoutes.SavedRoute)
            })
        }

        composable<NavigationRoutes.DetailRoute>(
            typeMap = customArgTypes
        ) { backStackEntry ->

            val detailRoute: NavigationRoutes.DetailRoute = backStackEntry.toRoute()
            val newsData = detailRoute.news

            DetailScreen(
                news = newsData,
                onBackClick = {
                    navigateToBackStack(navController)
                }
            )
        }

        composable<NavigationRoutes.DropDownRoute> {
            DropDownScreen { navigateToBackStack(navController) }
        }

        composable<NavigationRoutes.SavedRoute>() {
            SavedScreen({ navigateToBackStack(navController) }, onCardClicked = { selectedNews ->
                navController.safeNavigate(
                    NavigationRoutes.DetailRoute(selectedNews)
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