package com.example.moviescleanarchitectureapp.core.data.models

import com.example.moviescleanarchitectureapp.core.domain.models.MovieListModel

data class MovieDetailDTO(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val media_type: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDetailDTO.toDomainModel() =
    MovieListModel(
        this.id,
        this.title,
        this.poster_path
    )