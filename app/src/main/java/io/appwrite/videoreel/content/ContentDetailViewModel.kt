package io.appwrite.videoreel.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.videoreel.core.BaseViewModel
import io.appwrite.videoreel.feed.ContentDataSource
import io.appwrite.videoreel.model.Movie
import io.appwrite.videoreel.model.Show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContentDetailViewModel(
    client: Client,
    movieId: String?,
    showId: String?
) : BaseViewModel() {

    private val contentDataSource = ContentDataSource(client)

    private val _movies = MutableLiveData<List<Movie>>()
    private val _shows = MutableLiveData<List<Show>>()

    val movies: LiveData<List<Movie>> = _movies
    val shows: LiveData<List<Show>> = _shows

    fun getMovie() {
        viewModelScope.launch {
            val movies = contentDataSource.getMovie(movieId)

            withContext(Dispatchers.Main) {
                _movies.value = movies
            }
        }
    }

    fun getShows() {
        viewModelScope.launch {
            val shows = contentDataSource.getShows()

            withContext(Dispatchers.Main) {
                _shows.value = shows
            }
        }
    }
}