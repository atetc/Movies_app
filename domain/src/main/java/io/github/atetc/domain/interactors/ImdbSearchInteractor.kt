package io.github.atetc.domain.interactors

import io.github.atetc.domain.movie.MovieSearchParameters
import io.github.atetc.domain.movie.MovieShort
import io.github.atetc.domain.mappers.Mapper
import io.github.atetc.domain.mappers.imdb.MovieSearch
import io.github.atetc.domain.mappers.imdb.SearchItemToMovieShortMapper
import io.github.atetc.data.api.OmdbNetworkRepository
import io.github.atetc.data.dto.SearchItem
import kotlinx.coroutines.CoroutineDispatcher

class ImdbSearchInteractor(
    dispatcher: CoroutineDispatcher,
    private val omdbNetworkRepository: OmdbNetworkRepository,
) : BaseInteractor<MovieSearchParameters, MovieSearch>(dispatcher) {
    private val mapper: Mapper<SearchItem, MovieShort> = SearchItemToMovieShortMapper()

    override suspend fun action(input: MovieSearchParameters): MovieSearch {
        val result = omdbNetworkRepository.getMoviesList(
            input.title,
            input.type,
            input.page
        )

        return when {
            result.error != null -> MovieSearch.Error(result.error.orEmpty())
            result.search != null -> MovieSearch.Success(result.search.orEmpty().map(mapper::map))
            else -> MovieSearch.Error("Unknown error")
        }
    }
}
