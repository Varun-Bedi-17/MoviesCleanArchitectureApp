package com.example.moviescleanarchitectureapp.core.presentation.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviescleanarchitectureapp.core.domain.use_cases.GetMovieDetailsUseCase
import com.example.moviescleanarchitectureapp.core.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val getMovieDetailsUseCase: GetMovieDetailsUseCase) : ViewModel() {
    private val _moviesDetails = MutableStateFlow(MovieDetailState())
    val moviesList = _moviesDetails

    fun getMovieDetailsFromViewModel(movieId : String){
        getMovieDetailsUseCase(movieId.toInt()).onEach {
            when (it) {
                is ResponseState.Loading -> {
                    _moviesDetails.value = MovieDetailState(isLoading = true)
                }
                is ResponseState.Error -> {
                    _moviesDetails.value = MovieDetailState(error = it.message ?: "Something unexpected occur")
                }
                is ResponseState.Success -> {
                    if (it.data != null)
                        _moviesDetails.value = MovieDetailState(data = it.data)
                    else
                        _moviesDetails.value = MovieDetailState(error = "Empty List")
                }
            }
        }.launchIn(viewModelScope)
    }
}