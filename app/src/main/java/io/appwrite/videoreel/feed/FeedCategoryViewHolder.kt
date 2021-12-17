package io.appwrite.videoreel.feed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.appwrite.videoreel.BR
import io.appwrite.videoreel.databinding.ItemFeedCategoryBinding
import io.appwrite.videoreel.model.Movie

class FeedCategoryViewHolder(
    private val binding: ItemFeedCategoryBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Pair<String, List<Movie>>, onNestedItemSelected: (View, Movie) -> Unit) {
        val vm = FeedCategoryViewModel(
            categoryName = item.first,
            movies = item.second
        )
        val adapter = ContentCellAdapter(onNestedItemSelected)

        binding.setVariable(BR.viewModel, vm)
        binding.categoryRecycler.adapter = adapter

        adapter.submitList(item.second)
    }
}