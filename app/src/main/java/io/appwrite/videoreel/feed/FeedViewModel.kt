package io.appwrite.videoreel.feed

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.videoreel.core.BaseViewModel
import io.appwrite.videoreel.model.Movie
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModel(client: Client) : BaseViewModel() {

    private val feedDataSource = ContentDataSource(client)

    private val _moviesByCategory = MutableLiveData<List<Pair<String, List<Movie>>>>()
    private val _selectedMovie = MutableLiveData<Pair<View, Movie>>()

    val moviesByCategory: LiveData<List<Pair<String, List<Movie>>>> = _moviesByCategory
    val selectedMovie: LiveData<Pair<View, Movie>> = _selectedMovie

    fun getMovies() {
        viewModelScope.launch {
            val movies = feedDataSource.getMovies()
            val genres = movies.flatMap { it.genres }.toHashSet()
            val groups = genres.map { genre ->
                genre to movies.filter { movie ->
                    movie.genres.contains(genre)
                }
            }

            withContext(Main) {
                _moviesByCategory.value = groups
            }
        }
    }

    fun contentSelected(view: View, movie: Movie) {
        _selectedMovie.value = Pair(view, movie)
    }
}