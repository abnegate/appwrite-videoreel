package io.appwrite.videoreel.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.appwrite.Client

class ContentDetailViewModelFactory(
    private val client: Client,
    private val movieId: String,
    private val showId: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        ContentDetailViewModel(client, movieId, showId) as T
}