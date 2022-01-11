package io.appwrite.videoreel.feed

import io.appwrite.Client
import io.appwrite.services.Database
import io.appwrite.services.Realtime
import io.appwrite.videoreel.core.Configuration
import io.appwrite.videoreel.model.Movie

class ContentDataSource(
    private val client: Client,
) {
    private val database by lazy { Database(client) }
    private val realtime by lazy { Realtime(client) }

    @Throws
    suspend fun getMovies(): List<Movie> {
        val movieDocuments = database.listDocuments(Configuration.MOVIE_COLLECTION_ID)
        val movies = movieDocuments.documents.map { Movie(it.data) }.toMutableList()
        // TODO: Remove test duplicated movies
        for (i in 0 until 1000) {
            movies += movies[0]
        }
        return movies
    }

    @Throws
    suspend fun getMovie(movieId: String): Movie {
        val movie = database.getDocument(
            Configuration.MOVIE_COLLECTION_ID,
            movieId
        )
        return Movie(movie.data)
    }

    fun subscribeToMovies(action: (movie: Movie) -> Unit) {
        realtime.subscribe(
            "collections.${Configuration.MOVIE_COLLECTION_ID}.documents",
            payloadType = Movie::class.java
        ) {
            action(it.payload)
        }
    }
}