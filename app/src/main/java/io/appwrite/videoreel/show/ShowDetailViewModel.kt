package io.appwrite.videoreel.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.videoreel.core.BaseViewModel
import io.appwrite.videoreel.core.Messages
import io.appwrite.videoreel.feed.ContentDataSource
import io.appwrite.videoreel.model.Show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowDetailViewModel(
    client: Client,
    movieId: String,
) : BaseViewModel() {

    private val contentDataSource = ContentDataSource(client)

    private val _show = MutableLiveData<Show>()

    val show: LiveData<Show> = _show

    fun getShow(showId: String) {
        viewModelScope.launch {
            val show = contentDataSource.getShow(showId)
            if (show == null) {
                postMessage(Messages.GET_MOVIE_FAILED)
                return@launch
            }
            withContext(Dispatchers.Main) {
                _show.value = show
            }
        }
    }
}