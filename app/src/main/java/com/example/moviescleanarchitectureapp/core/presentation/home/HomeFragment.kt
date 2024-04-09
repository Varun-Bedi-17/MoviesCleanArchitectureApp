package com.example.moviescleanarchitectureapp.core.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviescleanarchitectureapp.R
import com.example.moviescleanarchitectureapp.core.domain.models.MovieListModel
import com.example.moviescleanarchitectureapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeMoviesAdapter.OnMovieListener {
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val homeMoviesAdapter by lazy {
        HomeMoviesAdapter(this)
    }
    private lateinit var movieDetailsList: List<MovieListModel>
    private var filteredMovieDetailsList: MutableList<MovieListModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater)

        observeMoviesList()
        setUpSearchView()
        return fragmentHomeBinding.root
    }

    /**
     * Sets up the search view and its text change listener.
     */
    private fun setUpSearchView() {
        fragmentHomeBinding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMovies(newText)
                return true
            }
        })
    }

    /**
     * Observes the movies list LiveData from the view model.
     * Sets up UI according to the state changes.
     */
    private fun observeMoviesList() {
        lifecycleScope.launch {
            viewModel.getMoviesListFromViewModel()
            viewModel.moviesList.collect { state ->
                handleMoviesListState(state)
            }
        }
    }

    /**
     * Handles the state changes of the movies list LiveData.
     * Updates UI components based on the state.
     */
    private fun handleMoviesListState(state: HomeFragmentState) {
        with(fragmentHomeBinding) {
            clProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

            if (state.error.isNotEmpty()) {
                Toast.makeText(activity, state.error, Toast.LENGTH_SHORT).show()
            }

            state.data.takeIf { it.isNotEmpty() }?.let {
                movieDetailsList = it
                setUpMovieListAdapter()
            }
        }
    }

    /**
     * Sets up the movie list adapter with the provided movie list.
     * Configures the RecyclerView with the adapter and layout manager.
     */
    private fun setUpMovieListAdapter() {
        fragmentHomeBinding.rvMovies.apply {
            layoutManager =
                GridLayoutManager(
                    requireContext(),
                    3
                )
            homeMoviesAdapter.submitList(
                movieDetailsList
            )
            adapter = homeMoviesAdapter
        }
    }


    /**
     * Filters the movies list based on the provided query.
     * Updates the adapter with the filtered list.
     */
    private fun filterMovies(query: String?) {
        filteredMovieDetailsList.clear()
        val filteredList = if (query.isNullOrBlank()) {
            movieDetailsList
        } else {
            movieDetailsList.filter { it.title.contains(query, ignoreCase = true) }
        }
        homeMoviesAdapter.submitList(filteredList)
    }

    override fun onMovieCallback(movieId: Int) {
        val bundle = Bundle()
        bundle.putInt("movieId", movieId)
        findNavController().navigate(
            R.id.action_homeFragment_to_movieDetailFragment,
            bundle
        )
    }

}