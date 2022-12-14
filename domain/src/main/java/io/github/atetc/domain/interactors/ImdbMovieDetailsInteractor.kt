package io.github.atetc.domain.interactors

import io.github.atetc.domain.movie.Movie
import io.github.atetc.domain.mappers.Mapper
import io.github.atetc.domain.mappers.imdb.DetailsResponseToMovieMapper
import io.github.atetc.domain.mappers.imdb.MovieDetails
import io.github.atetc.data.api.OmdbNetworkRepository
import io.github.atetc.data.dto.MovieDetailResponse
import kotlinx.coroutines.CoroutineDispatcher

class ImdbMovieDetailsInteractor(
    dispatcher: CoroutineDispatcher,
    private val omdbNetworkRepository: OmdbNetworkRepository,
) : BaseInteractor<String, MovieDetails>(dispatcher) {
    private val mapper: Mapper<MovieDetailResponse, Movie> = DetailsResponseToMovieMapper()

    override suspend fun action(input: String): MovieDetails {
        val result = omdbNetworkRepository.getMovieDetail(input)

        return when {
            result.error != null -> MovieDetails.Error(result.error.orEmpty())
            else -> MovieDetails.Success(mapper.map(result))
        }
    }
}
