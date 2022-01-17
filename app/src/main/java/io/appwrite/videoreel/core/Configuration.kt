package io.appwrite.videoreel.core

import io.appwrite.Query
import io.appwrite.videoreel.model.Category

object Configuration {
    const val ENDPOINT = "http://192.168.20.7/v1"
    const val PROJECT_ID = "test"
    const val MOVIE_COLLECTION_ID = "movies"

    val categories = listOf(
        Category(
            title = "Popular this week",
            queries = listOf(),
            orderAttributes = listOf("trendingIndex"),
            orderTypes = listOf("DESC")
        ),
        Category(
            title = "Only on Almost Netlfix",
            queries = listOf(Query.equal("isOriginal", true)),
            orderAttributes = listOf("trendingIndex"),
            orderTypes = listOf("DESC")
        ),
        Category(
            title = "New Releases",
            queries = listOf(Query.greaterEqual("releaseDate", 2018)),
            orderAttributes = listOf("releaseDate"),
            orderTypes = listOf("DESC")
        ),
        Category(
            title = "Movies longers than 2 hours",
            queries = listOf(Query.greaterEqual("durationMinutes", 120)),
            orderAttributes = listOf("durationMinutes"),
            orderTypes = listOf("DESC")
        ),
        Category(
            title = "Love is in the air",
            queries = listOf(Query.search("genres", "romance")),
            orderAttributes = listOf("trendingIndex"),
            orderTypes = listOf("DESC")
        ),
        Category(
            title = "Animated worlds",
            queries = listOf(Query.search("genres", "Animation")),
            orderAttributes = listOf("trendingIndex"),
            orderTypes = listOf("DESC")
        ),
        Category(
            title = "It is getting scary",
            queries = listOf(Query.search("genres", "Horror")),
            orderAttributes = listOf("trendingIndex"),
            orderTypes = listOf("DESC")
        ),
        Category(
            title = "Sci-Fi awaits...",
            queries = listOf(Query.search("genres", "Science Fiction")),
            orderAttributes = listOf("trendingIndex"),
            orderTypes = listOf("DESC")
        ),
        Category(
            title = "SAnime",
            queries = listOf(Query.search("tags", "Anime")),
            orderAttributes = listOf("trendingIndex"),
            orderTypes = listOf("DESC")
        ),
    )
}