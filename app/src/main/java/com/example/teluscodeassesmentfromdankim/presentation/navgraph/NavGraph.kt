package com.example.teluscodeassesmentfromdankim.presentation.navgraph

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teluscodeassesmentfromdankim.presentation.details.DetailScreen
import com.example.teluscodeassesmentfromdankim.presentation.details.DetailsViewModel
import com.example.teluscodeassesmentfromdankim.presentation.home.HomeScreen
import com.example.teluscodeassesmentfromdankim.presentation.home.HomeViewModel
import com.example.teluscodeassesmentfromdankim.presentation.similarmovie.SimilarMoviesUiEvent
import com.example.teluscodeassesmentfromdankim.presentation.similarmovie.SimilarMoviesViewModel

/**
 * Author: Dan Kim
 * Navigation graph for the app
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
                onNavigateToDetails = { movieId ->
                    navController.navigate(ScreenRoute.Details(id = movieId))
                },
                onEvent = homeViewModel::onEvent
            )
        }
        composable<ScreenRoute.Details> {

            val detailsViewModel = hiltViewModel<DetailsViewModel>()
            val similarMoviesViewModel = hiltViewModel<SimilarMoviesViewModel>()

            // Retrieve the movie from DetailsViewModel's state
            val movie = detailsViewModel.state.movie
            LaunchedEffect(key1 = movie?.id) {
                // Trigger fetching of similar movies for the current movie ID
                movie?.run {
                    similarMoviesViewModel.onEvent(event = SimilarMoviesUiEvent.FetchMovieId(movieId = id))
                }
            }

            DetailScreen(
                state = detailsViewModel.state,
                similarMoviesState = similarMoviesViewModel.state,
                onNavigateToHome = {
                    navController.popBackStack()
                }
            )
        }
    }
}