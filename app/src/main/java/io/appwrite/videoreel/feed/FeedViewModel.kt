package io.appwrite.videoreel.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.videoreel.core.BaseViewModel
import io.appwrite.videoreel.model.Movie
import io.appwrite.videoreel.model.Show
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModel(client: Client) : BaseViewModel() {

    private val feedDataSource = ContentDataSource(client)

    private val _movies = MutableLiveData<List<Movie>>()
    private val _shows = MutableLiveData<List<Show>>()

    val movies: LiveData<List<Movie>> = _movies
    val shows: LiveData<List<Show>> = _shows

    fun getMovies() {
        viewModelScope.launch {
            val movies = feedDataSource.getMovies()

            withContext(Main) {
                _movies.value = movies
            }
        }
    }

    fun getShows() {
        viewModelScope.launch {
            val shows = feedDataSource.getShows()

            withContext(Main) {
                _shows.value = shows
            }
        }
    }
}