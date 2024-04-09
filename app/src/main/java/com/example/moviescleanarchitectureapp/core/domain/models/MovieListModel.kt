package com.example.moviescleanarchitectureapp.core.domain.models

/**
 * Represents a movie list model.
 * @property movieId The unique identifier of the movie.
 * @property title The title of the movie.
 * @property posterUrl The URL of the movie poster.
 */
data class MovieListModel (
    var movieId : Int,
    val title: String,
    val posterUrl: String
)