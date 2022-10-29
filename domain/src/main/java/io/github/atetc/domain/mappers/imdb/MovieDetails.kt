package io.github.atetc.domain.mappers.imdb

import io.github.atetc.domain.movie.Movie

sealed class MovieDetails {
    data class Success(val movie: Movie) : MovieDetails()
    data class Error(val message: String) : MovieDetails()
}