package io.github.atetc.omdbapi.api

import io.github.atetc.omdbapi.BuildConfig
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class OmdbApiIntegrationTest {
    private val api =  OmdbApiFactory.build(BuildConfig.omdbToken)

    @Test
    fun getMovieDetailsIntegrationTest() = runBlocking {
        val result = api.getMovieDetail("tt1392190")
        assertEquals("Mad Max: Fury Road", result.title)
        assertEquals("2015", result.year)
        assertEquals("R", result.rated)
        assertEquals("15 May 2015", result.released)
        assertEquals("120 min", result.runtime)
        assertEquals("Action, Adventure, Sci-Fi", result.genre)
        assertEquals("George Miller", result.director)
        assertEquals("George Miller, Brendan McCarthy, Nick Lathouris", result.writer)
        assertEquals("Tom Hardy, Charlize Theron, Nicholas Hoult", result.actors)
        assertEquals(
            "In a post-apocalyptic wasteland, a woman rebels against a tyrannical ruler in search for her homeland with the aid of a group of female prisoners, a psychotic worshiper, and a drifter named Max.",
            result.plot
        )
        assertEquals("English, Russian", result.language)
        assertEquals("Australia, United States", result.country)
        assertEquals("Won 6 Oscars. 247 wins & 233 nominations total", result.awards)
        assertEquals(
            "https://m.media-amazon.com/images/M/MV5BN2EwM2I5OWMtMGQyMi00Zjg1LWJkNTctZTdjYTA4OGUwZjMyXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
            result.poster
        )
        assertEquals(3, result.ratings.size)
        assertEquals("90", result.metascore)
        assertEquals("8.1", result.imdbRating)
        assertEquals("966,247", result.imdbVotes)
        assertEquals("tt1392190", result.imdbID)
        assertEquals("movie", result.type)
        assertEquals("01 Sep 2015", result.dVD)
        assertEquals("$154,109,060", result.boxOffice)
        assertEquals("N/A", result.production)
        assertEquals("N/A", result.website)
        assertEquals("True", result.response)
    }

    @Test
    fun shortSearchTest() = runBlocking {
        val result = api.getMoviesList("M", "movie", 1)
        assertEquals("Too many results.", result.error)
    }

    @Test
    fun unknownSearchTest() = runBlocking {
        val result = api.getMoviesList("SOME UNKNOWN FILM", "movie", 1)
        assertEquals("Movie not found!", result.error)
    }

    @Test
    fun outOfPaginationSearchTest() = runBlocking {
        val result = api.getMoviesList("Max", "movie", 500)
        assertEquals("Movie not found!", result.error)
    }

    @Test
    fun normalSearchTest() = runBlocking {
        val result = api.getMoviesList("Max", "movie", 1)
        assertEquals(10, result.search?.size)
    }
}
