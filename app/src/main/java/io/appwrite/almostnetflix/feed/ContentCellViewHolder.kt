package io.appwrite.almostnetflix.feed

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.appwrite.almostnetflix.BR
import io.appwrite.almostnetflix.databinding.ItemContentCellBinding
import io.appwrite.almostnetflix.model.Movie

class ContentCellViewHolder(
    private val binding: ItemContentCellBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Movie, onItemClicked: (View, Movie) -> Unit) {
        val vm = ContentCellViewModel(item)
        binding.contentCard.setOnClickListener {
            onItemClicked(it, item)
        }
        binding.setVariable(BR.viewModel, vm)
    }
}