package io.appwrite.almostnetflix.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.almostnetflix.core.BaseViewModel
import io.appwrite.almostnetflix.feed.ContentDataSource
import io.appwrite.almostnetflix.model.Movie
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MovieDetailViewModel(
    client: Client,
    private val userId: String,
    private val movieId: String,
) : BaseViewModel() {

    private val movieDataSource = ContentDataSource(client)

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _releaseYear = MutableLiveData<String>()
    val releaseYear: LiveData<String> = _releaseYear

    private val _duration = MutableLiveData<String>()
    val duration: LiveData<String> = _duration

    init {
        getMovie()
    }

    fun toggleInWatchlist() {
        viewModelScope.launch {
//            if (watchlistIds.contains(movie.value?.id)) {
//                movieDataSource.removeFromWatchlist(userId, movie.value!!.id)
//                watchlistIds.remove(movie.value!!.id)
//            } else {
//                movieDataSource.addToWatchlist(userId, movie.value!!.id)
//                watchlistIds.add(movie.value!!.id)
//            }
//            getWatchlist()
        }
    }

    private fun getReleaseYear() {
        with(Calendar.getInstance()) {
            timeInMillis = movie.value!!.releaseDate

            _releaseYear.value = get(Calendar.YEAR).toString()
        }
    }

    private fun getDuration() {
        val mins = movie.value!!.durationMinutes.toInt()
        val hours = mins / 60
        val minutes = mins % 60
        _duration.value = "${hours}h ${minutes}m"
    }

    private fun getMovie() {
        viewModelScope.launch {
            val movie = movieDataSource.getMovie(movieId)

            withContext(Main) {
                _movie.value = movie
                getDuration()
                getReleaseYear()
            }
        }
    }
}