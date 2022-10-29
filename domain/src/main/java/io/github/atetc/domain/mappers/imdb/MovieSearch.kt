package io.github.atetc.domain.mappers.imdb

import io.github.atetc.domain.movie.MovieShort

sealed class MovieSearch {
    data class Success(val movies: List<MovieShort>) : MovieSearch()
    data class Error(val message: String) : MovieSearch()
}