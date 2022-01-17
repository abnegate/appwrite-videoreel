package io.appwrite.videoreel.feed

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.videoreel.core.BaseViewModel
import io.appwrite.videoreel.core.Configuration
import io.appwrite.videoreel.model.Category
import io.appwrite.videoreel.model.Movie
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModel(client: Client) : BaseViewModel() {

    private val feedDataSource = ContentDataSource(client)

    private val _moviesByCategory = MutableLiveData<MutableMap<Category, MutableSet<Movie>>>()
    private val _selectedMovie = MutableLiveData<Pair<View, Movie>>()

    val moviesByCategory: LiveData<MutableMap<Category, MutableSet<Movie>>> = _moviesByCategory
    val selectedMovie: LiveData<Pair<View, Movie>> = _selectedMovie

    private val movieSets = mutableMapOf<Category, MutableSet<Movie>>()

    fun getMovies() {
        viewModelScope.launch {
            for (category in Configuration.categories) {
                viewModelScope.launch {
                    val movies = feedDataSource.getMovies(category)

                    if (!movieSets.containsKey(category)) {
                        movieSets[category] = movies.toMutableSet()
                    }

                    movieSets[category]?.addAll(movies)

                    withContext(Main) {
                        _moviesByCategory.value = movieSets
                    }
                }
            }
        }
    }

    fun contentSelected(view: View, movie: Movie) {
        _selectedMovie.value = Pair(view, movie)
    }
}