package com.example.moviescleanarchitectureapp.core.presentation.movie_details

import com.example.moviescleanarchitectureapp.core.domain.models.MovieDetailsModel

/**
 * Represents the state of the movie details.
 * @property isLoading Indicates if the movie details are currently being loaded.
 * @property error Represents any error that occurred while fetching movie details.
 * @property data The MovieDetailsModel representing the movie details if available.
 */
data class MovieDetailState(
    val isLoading : Boolean = false,
    val error : String = "",
    val data : MovieDetailsModel? = null
)
