package io.appwrite.videoreel.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.appwrite.videoreel.R
import io.appwrite.videoreel.databinding.ItemContentCellBinding
import io.appwrite.videoreel.model.Movie

class ContentCellAdapter :
    ListAdapter<Movie, ContentCellViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentCellViewHolder {
        return ContentCellViewHolder(
            ItemContentCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContentCellViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}