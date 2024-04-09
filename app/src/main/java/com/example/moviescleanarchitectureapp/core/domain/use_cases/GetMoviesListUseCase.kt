package com.example.moviescleanarchitectureapp.core.domain.use_cases

import com.example.moviescleanarchitectureapp.core.data.models.toDomainModel
import com.example.moviescleanarchitectureapp.core.domain.models.MovieListModel
import com.example.moviescleanarchitectureapp.core.domain.repositories.GetMoviesListRepo
import com.example.moviescleanarchitectureapp.core.utils.Constants
import com.example.moviescleanarchitectureapp.core.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMoviesListUseCase @Inject constructor(
    private val getMoviesListRepo: GetMoviesListRepo,
) {
    operator fun invoke(): Flow<ResponseState<List<MovieListModel>>> = flow {
        try {
            emit(ResponseState.Loading())
            val response = getMoviesListRepo.getMoviesListFromRepo(Constants.AUTH_KEY, Constants.LANGUAGE)
            if (response.isSuccessful) {
                val moviesList = response.body()?.results
                    ?.map { it.toDomainModel() }
                emit(ResponseState.Success(data = moviesList ?: emptyList()))
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