package com.example.moviescleanarchitectureapp.core.data.models

data class MoviesListDTO(
    val page: Int,
    val results: List<MovieDetailDTO>,
    val total_pages: Int,
    val total_results: Int
)