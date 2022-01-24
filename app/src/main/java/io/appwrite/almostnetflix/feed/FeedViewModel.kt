package io.appwrite.almostnetflix.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.almostnetflix.core.BaseViewModel
import io.appwrite.almostnetflix.core.Configuration
import io.appwrite.almostnetflix.model.Category
import io.appwrite.almostnetflix.model.Movie
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModel(
    client: Client,
    private val userId: String,
) : BaseViewModel() {

    private val feedDataSource = ContentDataSource(client)

    private val _moviesByCategory = MutableLiveData<MutableMap<Category, MutableSet<Movie>>>()
    private val _selectedMovie = MutableLiveData<Movie>()
    private val _featuredMovie = MutableLiveData<Movie>()
    private val _watchlistMovies = MutableLiveData<List<Movie>>()

    val moviesByCategory: LiveData<MutableMap<Category, MutableSet<Movie>>> = _moviesByCategory
    val selectedMovie: LiveData<Movie> = _selectedMovie
    val featuredMovie: LiveData<Movie> = _featuredMovie
    val watchlistMovies: LiveData<List<Movie>> = _watchlistMovies

    private val watchlistIds = mutableListOf<String>()
    private val movieSets = mutableMapOf<Category, MutableSet<Movie>>()

    fun fetchFeaturedMovie() {
        viewModelScope.launch {
            val featured = feedDataSource.getFeaturedMovie() ?: return@launch

            withContext(Main) {
                _featuredMovie.value = featured
            }
        }
    }

    fun fetchMovies() {
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

    fun toggleInWatchlist() {
        viewModelScope.launch {
            if (watchlistIds.contains(featuredMovie.value?.id)) {
                feedDataSource.removeFromWatchlist(userId, featuredMovie.value!!.id)
                watchlistIds.remove(featuredMovie.value!!.id)
            } else {
                feedDataSource.addToWatchlist(userId, featuredMovie.value!!.id)
                watchlistIds.add(featuredMovie.value!!.id)
            }
            getWatchlist()
        }
    }

    fun featuredMovieSelected() {
        _selectedMovie.value = featuredMovie.value
    }

    fun movieSelected(movie: Movie) {
        _selectedMovie.value = movie
    }

    private fun getWatchlist() {
        viewModelScope.launch {
            val list = feedDataSource.getMyWatchlist(watchlistIds)
            _watchlistMovies.value = list
        }
    }
}