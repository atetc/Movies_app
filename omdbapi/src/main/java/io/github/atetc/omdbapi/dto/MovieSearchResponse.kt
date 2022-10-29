package io.github.atetc.omdbapi.dto

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
){
    fun isSuccess() = response.equals("True",true)

    fun getTotalItems() = totalResults?.toIntOrNull()?:0
}
