package com.example.moviescleanarchitectureapp.core.domain.models


/**
 * Represents a movie details model.
 * @property movieId The unique identifier of the movie.
 * @property title The title of the movie.
 * @property posterUrl The URL of the movie poster.
 * @property description The description or summary of the movie.
 * @property year The release year of the movie.
 */
data class MovieDetailsModel(
    var movieId : Int,
    val title: String,
    val posterUrl: String,
    val description : String,
    val year : String
)
