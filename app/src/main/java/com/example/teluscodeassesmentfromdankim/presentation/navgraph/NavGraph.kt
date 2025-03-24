package com.example.teluscodeassesmentfromdankim.presentation.navgraph

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.teluscodeassesmentfromdankim.presentation.details.DetailScreen
import com.example.teluscodeassesmentfromdankim.presentation.details.DetailsViewModel
import com.example.teluscodeassesmentfromdankim.presentation.home.HomeScreen
import com.example.teluscodeassesmentfromdankim.presentation.home.HomeViewModel

/**
 * Author: Dan Kim
 */

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home,
        enterTransition = { slideInHorizontally { it } },
        exitTransition = { slideOutHorizontally { -it } },
        popEnterTransition = { slideInHorizontally { -it } },
        popExitTransition = { slideOutHorizontally { it } },
    ) {
        composable<ScreenRoute.Home> {

            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                state = homeViewModel.state,
                onNavigateToDetails = { movieId->
                    navController.navigate(ScreenRoute.Details(id = movieId))
                },
                onEvent = homeViewModel::onEvent
            )
        }
        composable<ScreenRoute.Details> { navBackStackEntry ->

            val detailScreen: ScreenRoute.Details = navBackStackEntry.toRoute()

            val detailsViewModel = hiltViewModel<DetailsViewModel>()
            val seeMe = detailsViewModel.movieId
            println("seeMe: $seeMe")

            DetailScreen(
                onNavigateToHome = {
                    navController.popBackStack()
                }
            )
        }
    }
}