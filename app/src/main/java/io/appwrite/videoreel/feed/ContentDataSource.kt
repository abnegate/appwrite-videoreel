package io.appwrite.videoreel.feed

import io.appwrite.Client
import io.appwrite.services.Database
import io.appwrite.services.Realtime
import io.appwrite.videoreel.core.Configuration
import io.appwrite.videoreel.model.Movie
import io.appwrite.videoreel.model.Show
import io.appwrite.videoreel.model.ShowEpisode
import io.appwrite.videoreel.model.ShowSeason

class ContentDataSource(
    private val client: Client,
) {
    private val database by lazy { Database(client) }
    private val realtime by lazy { Realtime(client) }

    @Throws
    suspend fun getMovies(): List<Movie> {
        val movies = database
            .listDocuments(Configuration.MOVIE_COLLECTION_ID)
        return movies.documents.map { Movie(it.data) }
    }

    @Throws
    suspend fun getShows(): List<Show> {
        val shows = database
            .listDocuments(Configuration.SHOW_COLLECTION_ID)
        return shows.documents.map { Show(it.data) }
    }

    @Throws
    suspend fun getMovie(movieId: String): Movie? {
        val movie = database.getDocument(
            Configuration.MOVIE_COLLECTION_ID,
            movieId
        )
        return Movie(movie.data)
    }

    @Throws
    suspend fun getShow(showId: String): Show? {
        val show = database.getDocument(
            Configuration.SHOW_COLLECTION_ID,
            showId
        )
        return Show(show.data)
    }

    @Throws
    suspend fun getShowSeasons(showId: String): List<ShowSeason> {
        val showSeasons = database.listDocuments(
            Configuration.SHOW_SEASON_COLLECTION_ID,
            listOf("showId", "equal", showId)
        )
        return showSeasons.documents.map { ShowSeason(it.data) }
    }

    @Throws
    suspend fun getShows(showSeasonId: String): List<ShowEpisode> {
        val showSeasonEpisodes = database.listDocuments(
            Configuration.SHOW_EPISODE_COLLECTION_ID,
            listOf("showSeasonId", "equal", showSeasonId)
        )
        return showSeasonEpisodes.documents.map { ShowEpisode(it.data) }
    }

    fun subscribeToMovies(action: (movie: Movie) -> Unit) {
        realtime.subscribe(
            "collections.${Configuration.MOVIE_COLLECTION_ID}.documents",
            payloadType = Movie::class.java
        ) {
            action(it.payload)
        }
    }

    fun subscribeToShows(action: (show: Show) -> Unit) {
        realtime.subscribe(
            "collections.${Configuration.SHOW_COLLECTION_ID}.documents",
            payloadType = Show::class.java
        ) {
            action(it.payload)
        }
    }
}