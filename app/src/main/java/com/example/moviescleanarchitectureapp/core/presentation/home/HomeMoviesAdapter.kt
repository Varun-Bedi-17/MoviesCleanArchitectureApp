package com.example.moviescleanarchitectureapp.core.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviescleanarchitectureapp.R
import com.example.moviescleanarchitectureapp.core.domain.models.MovieListModel
import com.example.moviescleanarchitectureapp.core.utils.Constants
import com.example.moviescleanarchitectureapp.databinding.MoviesListItemBinding

class HomeMoviesAdapter(private val itemClickListener: OnMovieListener) : RecyclerView.Adapter<HomeMoviesAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(private val binding: MoviesListItemBinding) : RecyclerView.ViewHolder(binding.root){
        /**
         * Binds movie data to the ViewHolder.
         * @param movie The MovieListModel object to bind.
         */
        fun bind(movie: MovieListModel) {
            val imageUrl = Constants.IMAGE_BASE_URL + movie.posterUrl
            binding.apply {
                Glide.with(ivMovie.context)
                    .load(imageUrl)
                    .apply(RequestOptions().error(R.drawable.iv_placeholder))
                    .into(ivMovie)
                ivMovie.setOnClickListener {
                    itemClickListener.onMovieCallback(movie.movieId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: MoviesListItemBinding = MoviesListItemBinding.inflate(layoutInflater)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<MovieListModel>(){
        override fun areItemsTheSame(
            oldItem: MovieListModel,
            newItem: MovieListModel
        ) = oldItem.movieId == newItem.movieId

        override fun areContentsTheSame(
            oldItem: MovieListModel,
            newItem: MovieListModel
        ) = oldItem == newItem

    }
    private val differ = AsyncListDiffer(this, differCallback)

    /**
     * Submits a new list of movies to be displayed by the adapter.
     * @param moviesList The new list of movies.
     */
    fun submitList(moviesList : List<MovieListModel>?){
        differ.submitList(moviesList)
    }

    /**
     * Interface to handle movie click events.
     */
    interface OnMovieListener{
        /**
         * Called when a movie item is clicked.
         * @param movieId The ID of the clicked movie.
         */
        fun onMovieCallback(movieId : Int)
    }

}