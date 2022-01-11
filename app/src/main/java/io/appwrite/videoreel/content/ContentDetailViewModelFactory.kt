package io.appwrite.videoreel.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.appwrite.Client
import io.appwrite.videoreel.movie.MovieDetailViewModel

class ContentDetailViewModelFactory(
    private val client: Client,
    private val movieId: String?,
    private val showId: String?,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = when (modelClass) {
        MovieDetailViewModel::class.java -> MovieDetailViewModel(client, movieId!!) as T
        else -> throw UnsupportedOperationException()
    }
}