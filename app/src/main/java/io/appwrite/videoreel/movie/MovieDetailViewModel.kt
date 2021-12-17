package io.appwrite.videoreel.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.videoreel.core.BaseViewModel
import io.appwrite.videoreel.core.Messages
import io.appwrite.videoreel.feed.ContentDataSource
import io.appwrite.videoreel.model.Movie
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel(
    client: Client,
    private val movieId: String,
) : BaseViewModel() {

    private val contentDataSource = ContentDataSource(client)

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    init {
        getMovie()
    }

    private fun getMovie() {
        viewModelScope.launch {
            val movie = withContext(IO) {
                contentDataSource.getMovie(movieId)
            }
            if (movie == null) {
                postMessage(Messages.GET_MOVIE_FAILED)
                return@launch
            }
            _movie.value = movie!!
        }
    }
}