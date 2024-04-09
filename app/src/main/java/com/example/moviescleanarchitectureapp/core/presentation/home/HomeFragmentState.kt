package com.example.moviescleanarchitectureapp.core.presentation.home

import com.example.moviescleanarchitectureapp.core.domain.models.MovieListModel


/**
 * Represents the state of the HomeFragment.
 * @property isLoading Indicates if the fragment is in a loading state.
 * @property error Represents any error that occurred during data retrieval.
 * @property data The list of MovieListModel representing the movies data.
 */
data class HomeFragmentState(
    val isLoading : Boolean = false,
    val error : String = "",
    val data : List<MovieListModel> = emptyList()
)
