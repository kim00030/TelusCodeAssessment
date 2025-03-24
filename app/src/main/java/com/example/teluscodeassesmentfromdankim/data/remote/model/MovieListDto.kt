package com.example.teluscodeassesmentfromdankim.data.remote.model


import com.google.gson.annotations.SerializedName

data class MovieListDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)