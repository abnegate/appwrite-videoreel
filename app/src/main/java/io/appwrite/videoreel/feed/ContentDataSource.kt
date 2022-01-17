package io.appwrite.videoreel.feed

import io.appwrite.Client
import io.appwrite.services.Database
import io.appwrite.services.Realtime
import io.appwrite.videoreel.core.Configuration
import io.appwrite.videoreel.model.Category
import io.appwrite.videoreel.model.Movie

class ContentDataSource(
    private val client: Client,
) {
    private val database by lazy { Database(client) }
    private val realtime by lazy { Realtime(client) }

    @Throws
    suspend fun getMovies(
        category: Category,
    ): List<Movie> {
        val movieDocuments = database.listDocuments(
            category.collectionName ?: Configuration.MOVIE_COLLECTION_ID,
            category.queries,
            null,
            null,
            null,
            null,
            category.orderAttributes,
            category.orderTypes
        )
        return movieDocuments.documents
            .map { Movie(it.data) }
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