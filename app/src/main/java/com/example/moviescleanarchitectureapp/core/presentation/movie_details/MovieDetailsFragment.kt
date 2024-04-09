package com.example.moviescleanarchitectureapp.core.presentation.movie_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviescleanarchitectureapp.R
import com.example.moviescleanarchitectureapp.core.utils.Constants
import com.example.moviescleanarchitectureapp.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private var movieId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            movieId = args.getInt("movieId").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater)

        // Api cal to get movie details
        lifecycleScope.launch {
            viewModel.getMovieDetailsFromViewModel(movieId)
        }
        setupBackPressCallback()
        observeMovieDetails()

        return binding.root
    }

    /**
     * Sets up the back press callback to navigate up when back arrow is clicked.
     */
    private fun setupBackPressCallback() {
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigateUp()
        }
        binding.ivBackArrow.setOnClickListener {
            callback.handleOnBackPressed()
        }
    }

    /**
     * Observes changes in movie detail state from the ViewModel.
     */
    private fun observeMovieDetails() {
        lifecycleScope.launch {
            viewModel.moviesList.collect { state ->
                handleMovieDetailState(state)
            }
        }
    }

    /**
     * Handles the state of movie details received from the ViewModel.
     * Updates UI accordingly.
     * @param state The state of movie details.
     */
    private fun handleMovieDetailState(state: MovieDetailState) {
        with(binding) {
            if (state.error.isNotEmpty()) {
                Toast.makeText(activity, state.error, Toast.LENGTH_SHORT).show()
            }

            state.data?.let { movieDetails ->
                model = movieDetails
                val imageUrl = Constants.IMAGE_BASE_URL + movieDetails.posterUrl
                Glide.with(ivMoviePoster.context)
                    .load(imageUrl)
                    .apply(
                        RequestOptions()
                            .error(R.drawable.iv_movie_detail) // Error image in case of loading failure
                    )
                    .into(ivMoviePoster)
            }
        }
    }
}