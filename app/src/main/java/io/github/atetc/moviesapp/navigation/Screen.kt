package io.github.atetc.moviesapp.navigation

sealed class Screen(val route: String) {
    object Splash: Screen(Routes.SPLASH_SCREEN)
    object Movies: Screen(Routes.MOVIES_LIST_SCREEN)
    object Details: Screen("${Routes.MOVIE_DETAILS_SCREEN}/{movieId}") {
        fun createRoute(movieId: String) = "${Routes.MOVIE_DETAILS_SCREEN}/$movieId"
    }
}
