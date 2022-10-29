package io.github.atetc.omdbapi.api

import io.github.atetc.omdbapi.dto.MovieDetailResponse
import io.github.atetc.omdbapi.dto.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET(".")
    suspend fun getMoviesList(@Query("s") search:String,
                             @Query("type") type: String,
                             @Query("page") page:Int): MovieSearchResponse

    @GET(".")
    suspend fun getMovieDetail(@Query("i") movieId:String): MovieDetailResponse
}