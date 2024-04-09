package com.example.moviescleanarchitectureapp.core.data.repositories

import com.example.moviescleanarchitectureapp.core.data.models.MovieDetailsDTO
import com.example.moviescleanarchitectureapp.core.data.source.ApiService
import com.example.moviescleanarchitectureapp.core.domain.repositories.GetMovieDetailsRepo
import retrofit2.Response
import javax.inject.Inject

class GetMovieDetailsRepoImpl @Inject constructor(private val apiService: ApiService) :
    GetMovieDetailsRepo {
    override suspend fun getMovieDetailsFromRepo(
        authorization: String,
        movieId: Int,
        language: String
    ): Response<MovieDetailsDTO> = apiService.getMovieDetails(authorization,movieId, language)

}