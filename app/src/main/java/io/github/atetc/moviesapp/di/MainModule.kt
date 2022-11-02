package io.github.atetc.moviesapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.atetc.data.api.OmdbApiFactory
import io.github.atetc.data.api.OmdbNetworkRepository
import io.github.atetc.domain.interactors.ImdbMovieDetailsInteractor
import io.github.atetc.domain.interactors.ImdbSearchInteractor
import io.github.atetc.domain.interactors.Interactor
import io.github.atetc.domain.mappers.imdb.MovieDetails
import io.github.atetc.domain.mappers.imdb.MovieSearch
import io.github.atetc.domain.movie.MovieSearchParameters
import io.github.atetc.moviesapp.BuildConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Qualifier
    annotation class IoDispatcher

    @InstallIn(SingletonComponent::class)
    @Module
    object DispatchersModule {
        @Provides
        @IoDispatcher
        @Singleton
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }

    @Provides
    fun omdbApi() = OmdbApiFactory.build(BuildConfig.omdbToken)

    @Provides
    fun imdbSearchInteractor(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        omdbNetworkRepository: OmdbNetworkRepository
    ): Interactor<MovieSearchParameters, MovieSearch> = ImdbSearchInteractor(dispatcher, omdbNetworkRepository)

    @Provides
    fun imdbMovieDetailsInteractor(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        omdbNetworkRepository: OmdbNetworkRepository
    ): Interactor<String, MovieDetails> = ImdbMovieDetailsInteractor(dispatcher, omdbNetworkRepository)
}
