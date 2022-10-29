package io.github.atetc.moviesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import io.github.atetc.moviesapp.navigation.Screen
import io.github.atetc.moviesapp.presentation.details.MovieDetailsViewModel
import io.github.atetc.moviesapp.theme.MovieDetailsScreen
import io.github.atetc.moviesapp.theme.MoviesListScreen
import io.github.atetc.moviesapp.theme.SplashScreen
import io.github.atetc.moviesapp.ui.theme.MoviesAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesAppTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Splash.route
                ) {
                    composable(Screen.Splash.route) {
                        SplashScreen(navController = navController)
                    }
                    composable(Screen.Movies.route) {
                        MoviesListScreen(navController = navController, viewModel = hiltViewModel())
                    }
                    composable(Screen.Details.route, arguments = listOf(
                        navArgument("movieId") {
                            type = NavType.StringType
                        }
                    )) {
                        val movieId = it.arguments?.getString("movieId")
                        val viewModel = hiltViewModel<MovieDetailsViewModel>()
                        if (movieId != null) {
                            viewModel.getMovieDetail(movieId)
                        }
                        MovieDetailsScreen(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }
}
