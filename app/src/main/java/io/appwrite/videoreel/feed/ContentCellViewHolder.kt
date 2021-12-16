package io.appwrite.videoreel.feed

import androidx.recyclerview.widget.RecyclerView
import io.appwrite.videoreel.BR
import io.appwrite.videoreel.databinding.ItemContentCellBinding
import io.appwrite.videoreel.model.Movie

class ContentCellViewHolder(private val binding: ItemContentCellBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Movie) {
        val vm = ContentCellViewModel(item)
        binding.setVariable(BR.viewModel, vm)
    }
}