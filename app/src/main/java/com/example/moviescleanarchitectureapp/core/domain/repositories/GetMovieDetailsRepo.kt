package com.example.moviescleanarchitectureapp.core.domain.repositories

import com.example.moviescleanarchitectureapp.core.data.models.MovieDetailsDTO
import retrofit2.Response

interface GetMovieDetailsRepo {
    suspend fun getMovieDetailsFromRepo(authorization: String, movieId : Int,language : String) : Response<MovieDetailsDTO>
}