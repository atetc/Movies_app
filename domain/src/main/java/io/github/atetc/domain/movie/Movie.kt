package io.github.atetc.domain.movie

data class Movie(
    val imdbId: String,
    val title: String,
    val poster: String,
    val year: String,
    val plot: String,
    val actors: String,
    val language: String,
    val runtime: String
)
