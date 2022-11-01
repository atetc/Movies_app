package io.github.atetc.data.api

import io.github.atetc.data.dto.MovieDetailResponse
import io.github.atetc.data.dto.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbNetworkRepository {
    @GET(".")
    suspend fun getMoviesList(@Query("s") search:String,
                             @Query("type") type: String,
                             @Query("page") page:Int): MovieSearchResponse

    @GET(".")
    suspend fun getMovieDetail(@Query("i") movieId:String): MovieDetailResponse
}