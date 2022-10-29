package io.github.atetc.moviesapp.presentation.details

import io.github.atetc.domain.movie.Movie

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    data class Error(val message: String) : MovieDetailsState()
    data class Content(val movie: Movie) : MovieDetailsState()
}
