package io.github.atetc.moviesapp.presentation.list

import io.github.atetc.domain.movie.MovieShort

sealed class MoviesListState {
    object Loading : MoviesListState()
    data class Error(val message: String) : MoviesListState()
    data class Content(val content: List<MovieShort>) : MoviesListState()
}
