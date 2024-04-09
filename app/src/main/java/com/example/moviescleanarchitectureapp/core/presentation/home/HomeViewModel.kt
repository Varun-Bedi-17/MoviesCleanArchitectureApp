package com.example.moviescleanarchitectureapp.core.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviescleanarchitectureapp.core.domain.use_cases.GetMoviesListUseCase
import com.example.moviescleanarchitectureapp.core.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getMoviesListUseCase: GetMoviesListUseCase) : ViewModel() {
    private val _moviesList = MutableStateFlow(HomeFragmentState())
    val moviesList = _moviesList

    /**
     * Retrieves the list of movies from the use case and updates the state accordingly.
     */
    fun getMoviesListFromViewModel(){
        getMoviesListUseCase().onEach {
            when (it) {
                is ResponseState.Loading -> {
                    _moviesList.value = HomeFragmentState(isLoading = true)
                }
                is ResponseState.Error -> {
                    _moviesList.value = HomeFragmentState(error = it.message ?: "Something unexpected occur")
                }
                is ResponseState.Success -> {
                    if (it.data != null)
                        _moviesList.value = HomeFragmentState(data = it.data)
                    else
                        _moviesList.value = HomeFragmentState(error = "Empty List")
                }
            }
        }.launchIn(viewModelScope)
    }

}