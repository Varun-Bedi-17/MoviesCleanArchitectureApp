package com.example.moviescleanarchitectureapp.core.domain.use_cases

import com.example.moviescleanarchitectureapp.core.data.models.toDomainModel
import com.example.moviescleanarchitectureapp.core.domain.models.MovieDetailsModel
import com.example.moviescleanarchitectureapp.core.domain.repositories.GetMovieDetailsRepo
import com.example.moviescleanarchitectureapp.core.utils.Constants
import com.example.moviescleanarchitectureapp.core.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val getMovieDetailsRepo: GetMovieDetailsRepo,
) {
    operator fun invoke(
        movieId : Int
    ): Flow<ResponseState<MovieDetailsModel?>> = flow {
        try {
            emit(ResponseState.Loading())
            val response = getMovieDetailsRepo.getMovieDetailsFromRepo(Constants.AUTH_KEY, movieId, Constants.LANGUAGE)
            if (response.isSuccessful) {
                val moviesList = response.body()?.toDomainModel()
                emit(ResponseState.Success(data = moviesList))
            } else {
                emit(ResponseState.Error(message = response.message(), data = null))
            }
        } catch (e: HttpException) {
            emit(ResponseState.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(ResponseState.Error(message = e.localizedMessage ?: "Check your internet connection"))
        } catch (e: Exception) {
            emit(ResponseState.Error(message = e.localizedMessage ?: "Something went wrong"))
        }
    }
}