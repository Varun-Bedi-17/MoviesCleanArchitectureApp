package com.example.moviescleanarchitectureapp.core.di

import com.example.moviescleanarchitectureapp.core.data.repositories.GetMovieDetailsRepoImpl
import com.example.moviescleanarchitectureapp.core.data.repositories.GetMoviesListRepoImpl
import com.example.moviescleanarchitectureapp.core.data.source.ApiService
import com.example.moviescleanarchitectureapp.core.domain.repositories.GetMovieDetailsRepo
import com.example.moviescleanarchitectureapp.core.domain.repositories.GetMoviesListRepo
import com.example.moviescleanarchitectureapp.core.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModules {

    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)


    @Provides
    fun provideMoviesListRepo(apiService: ApiService) : GetMoviesListRepo =
        GetMoviesListRepoImpl(apiService)

    @Provides
    fun provideMovieDetailsRepo(apiService: ApiService) : GetMovieDetailsRepo =
        GetMovieDetailsRepoImpl(apiService)
}