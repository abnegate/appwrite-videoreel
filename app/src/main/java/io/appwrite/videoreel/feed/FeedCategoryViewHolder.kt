package io.appwrite.videoreel.feed

import androidx.recyclerview.widget.RecyclerView
import io.appwrite.videoreel.BR
import io.appwrite.videoreel.databinding.ItemFeedCategoryBinding
import io.appwrite.videoreel.model.Movie

class FeedCategoryViewHolder(private val binding: ItemFeedCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Pair<String, List<Movie>>) {
        val vm = FeedCategoryViewModel(
            categoryName = item.first,
            movies = item.second
        )
        binding.setVariable(BR.viewModel, vm)
        binding.categoryRecycler.adapter = ContentCellAdapter()
    }
}