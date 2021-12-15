package io.appwrite.videoreel.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.appwrite.Client
import io.appwrite.videoreel.content.ContentDetailFragment
import io.appwrite.videoreel.content.ContentDetailViewModel
import io.appwrite.videoreel.feed.FeedViewModel
import io.appwrite.videoreel.login.LoginViewModel

class ClientViewModelFactory(
    private val client: Client,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) = when (modelClass) {
        LoginViewModel::class.java -> {
            LoginViewModel(client) as T
        }
        FeedViewModel::class.java -> {
            FeedViewModel(client) as T
        }
        ContentDetailViewModel::class.java -> {
            ContentDetailViewModel(client) as T
        }
        else -> {
            throw UnsupportedOperationException()
        }
    }
}