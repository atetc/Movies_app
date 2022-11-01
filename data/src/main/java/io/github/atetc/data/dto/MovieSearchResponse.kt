package io.github.atetc.data.dto

import com.google.gson.annotations.SerializedName

data class MovieSearchResponse(
    @field:SerializedName("Response")
    val response: String? = null,

    @field:SerializedName("totalResults")
    val totalResults: String? = null,

    @field:SerializedName("Error")
    val error: String? = null,

    @field:SerializedName("Search")
    val search: ArrayList<SearchItem>? = null
)
