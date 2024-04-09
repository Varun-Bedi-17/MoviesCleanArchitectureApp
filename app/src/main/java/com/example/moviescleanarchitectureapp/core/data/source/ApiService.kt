package com.example.moviescleanarchitectureapp.core.data.source

import com.example.moviescleanarchitectureapp.core.data.models.MovieDetailsDTO
import com.example.moviescleanarchitectureapp.core.data.models.MoviesListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("/3/trending/movie/day")
    suspend fun getMovies(@Header("Authorization") authorization: String, @Query("language") language: String) : Response<MoviesListDTO>


    @Headers("Content-Type: application/json")
    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(@Header("Authorization") authorization: String, @Path("movie_id") movieId : Int, @Query("language") language: String) : Response<MovieDetailsDTO>

}