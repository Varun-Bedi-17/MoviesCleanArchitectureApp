package com.example.moviescleanarchitectureapp.core.domain.repositories

import com.example.moviescleanarchitectureapp.core.data.models.MoviesListDTO
import retrofit2.Response

interface GetMoviesListRepo {
    suspend fun getMoviesListFromRepo(authorization: String, language : String) : Response<MoviesListDTO>
}