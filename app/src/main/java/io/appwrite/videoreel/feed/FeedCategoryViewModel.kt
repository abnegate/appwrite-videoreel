package io.appwrite.videoreel.feed

import io.appwrite.videoreel.core.BaseViewModel
import io.appwrite.videoreel.model.Movie

class FeedCategoryViewModel(
    val categoryName: String,
    val movies: List<Movie>,
) : BaseViewModel()