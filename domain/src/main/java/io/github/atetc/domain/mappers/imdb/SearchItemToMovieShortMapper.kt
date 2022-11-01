package io.github.atetc.domain.mappers.imdb

import io.github.atetc.domain.movie.MovieShort
import io.github.atetc.domain.mappers.Mapper
import io.github.atetc.data.dto.SearchItem

internal class SearchItemToMovieShortMapper : Mapper<SearchItem, MovieShort> {
    override fun map(input: SearchItem) = MovieShort(
        imdbId = input.imdbID.orEmpty(),
        title = input.title.orEmpty(),
        poster = input.poster.orEmpty(),
        year = input.year.orEmpty()
    )
}