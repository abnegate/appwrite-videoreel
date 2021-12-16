package io.appwrite.videoreel.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.videoreel.core.BaseViewModel
import io.appwrite.videoreel.core.Messages
import io.appwrite.videoreel.feed.ContentDataSource
import io.appwrite.videoreel.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel(
    client: Client,
    movieId: String,
) : BaseViewModel() {

    private val contentDataSource = ContentDataSource(client)

    private val _movie = MutableLiveData<Movie>()

    val movie: LiveData<Movie> = _movie

    fun getMovie(movieId: String) {
        viewModelScope.launch {
            val movie = contentDataSource.getMovie(movieId)
            if (movie == null) {
                postMessage(Messages.GET_MOVIE_FAILED)
                return@launch
            }
            withContext(Dispatchers.Main) {
                _movie.value = movie
            }
        }
    }
}