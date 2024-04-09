package com.example.moviescleanarchitectureapp.core.data.repositories

import com.example.moviescleanarchitectureapp.core.data.models.MoviesListDTO
import com.example.moviescleanarchitectureapp.core.data.source.ApiService
import com.example.moviescleanarchitectureapp.core.domain.repositories.GetMoviesListRepo
import retrofit2.Response
import javax.inject.Inject

class GetMoviesListRepoImpl @Inject constructor(private val apiService: ApiService) :
    GetMoviesListRepo {
    override suspend fun getMoviesListFromRepo(
        authorization: String,
        language: String
    ): Response<MoviesListDTO> = apiService.getMovies(authorization, language)


}