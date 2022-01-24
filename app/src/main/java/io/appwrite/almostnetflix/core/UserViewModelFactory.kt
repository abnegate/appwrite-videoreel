package io.appwrite.almostnetflix.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.appwrite.Client
import io.appwrite.almostnetflix.feed.FeedViewModel

class UserViewModelFactory(
    private val client: Client,
    private val userId: String,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) = when (modelClass) {
        FeedViewModel::class.java -> {
            FeedViewModel(client, userId) as T
        }
        else -> {
            throw UnsupportedOperationException()
        }
    }

}
