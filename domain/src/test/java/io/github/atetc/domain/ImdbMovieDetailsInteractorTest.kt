package io.github.atetc.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.github.atetc.domain.interactors.ImdbMovieDetailsInteractor
import io.github.atetc.domain.mappers.imdb.MovieDetails
import io.github.atetc.data.api.OmdbNetworkRepository
import io.github.atetc.data.dto.MovieDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ImdbMovieDetailsInteractorTest {

    private val omdbNetworkRepository: OmdbNetworkRepository = mock()

    private val interactor = ImdbMovieDetailsInteractor(Dispatchers.Unconfined, omdbNetworkRepository)

    @Before
    fun before() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        Mockito.reset(omdbNetworkRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testSuccessAction() = runTest {
        whenever(omdbNetworkRepository.getMovieDetail("successId")).thenReturn(
            MovieDetailResponse(
                imdbID = "imdbID",
                title = "title",
                poster = "poster",
                year = "year",
                actors = "actors",
                plot = "plot",
                language = "language",
                runtime = "runtime"
            )
        )

        val result = interactor.action("successId")

        verify(omdbNetworkRepository).getMovieDetail("successId")

        assertEquals((result as MovieDetails.Success).movie.imdbId, "imdbID")
        assertEquals(result.movie.title, "title")
        assertEquals(result.movie.poster, "poster")
        assertEquals(result.movie.year, "year")
        assertEquals(result.movie.actors, "actors")
        assertEquals(result.movie.plot, "plot")
        assertEquals(result.movie.language, "language")
        assertEquals(result.movie.runtime, "runtime")
    }

    @Test
    fun testErrorAction() = runTest {
        whenever(omdbNetworkRepository.getMovieDetail("errorId")).thenReturn(MovieDetailResponse(error = "Error Message"))

        val result = interactor.action("errorId")

        verify(omdbNetworkRepository).getMovieDetail("errorId")
        assertEquals((result as MovieDetails.Error).message, "Error Message")
    }
}
