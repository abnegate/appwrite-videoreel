package io.appwrite.videoreel.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.appwrite.videoreel.databinding.ItemFeedCategoryBinding
import io.appwrite.videoreel.model.Movie

class FeedCategoryAdapter :
    ListAdapter<Pair<String, List<Movie>>, FeedCategoryViewHolder>(object :
        DiffUtil.ItemCallback<Pair<String, List<Movie>>>() {
        override fun areItemsTheSame(
            oldItem: Pair<String, List<Movie>>,
            newItem: Pair<String, List<Movie>>,
        ): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(
            oldItem: Pair<String, List<Movie>>,
            newItem: Pair<String, List<Movie>>,
        ): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedCategoryViewHolder {
        return FeedCategoryViewHolder(
            ItemFeedCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedCategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}