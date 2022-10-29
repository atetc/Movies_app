package io.github.atetc.moviesapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.atetc.domain.movie.MovieSearchParameters
import io.github.atetc.domain.interactors.ImdbMovieDetailsInteractor
import io.github.atetc.domain.interactors.ImdbSearchInteractor
import io.github.atetc.domain.interactors.Interactor
import io.github.atetc.domain.mappers.imdb.MovieDetails
import io.github.atetc.domain.mappers.imdb.MovieSearch
import io.github.atetc.omdbapi.BuildConfig
import io.github.atetc.omdbapi.api.OmdbApi
import io.github.atetc.omdbapi.api.OmdbApiFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class MainModule {
    @Provides
    fun omdbApi() = OmdbApiFactory.build(BuildConfig.omdbToken)

    @Provides
    @Named("ioDispatcher")
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun imdbSearchInteractor(
        @Named("ioDispatcher") dispatcher: CoroutineDispatcher,
        omdbApi: OmdbApi
    ): Interactor<MovieSearchParameters, MovieSearch> = ImdbSearchInteractor(dispatcher, omdbApi)

    @Provides
    fun imdbMovieDetailsInteractor(
        @Named("ioDispatcher") dispatcher: CoroutineDispatcher,
        omdbApi: OmdbApi
    ): Interactor<String, MovieDetails> = ImdbMovieDetailsInteractor(dispatcher, omdbApi)
}
