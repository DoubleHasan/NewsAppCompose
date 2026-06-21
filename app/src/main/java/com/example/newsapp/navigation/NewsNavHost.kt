package com.example.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                navController.navigate(DetailRoute(selectedNews))
            }, onLangCLick = {
                navController.navigate(DropDownRoute)
            }, onBookmarkClicked = {
                navController.navigate(SavedRoute)
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
                    navController.popBackStack()
                }
            )
        }

        composable<DropDownRoute> {
            DropDownScreen { navController.popBackStack() }
        }

        composable<SavedRoute>() {
            SavedScreen({ navController.popBackStack() }, onCardClicked = { selectedNews ->
                navController.navigate(
                    DetailRoute(selectedNews)
                )
            })
        }
    }
}