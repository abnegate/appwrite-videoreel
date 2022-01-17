package io.appwrite.videoreel.model

data class MoviePage(
    val movies: List<Movie>,
    val hasNext: Boolean
)