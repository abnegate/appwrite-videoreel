package io.appwrite.almostnetflix.core

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.launch

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String) {
    Glide
        .with(this)
        .load(url)
        .into(this)
}

@BindingAdapter("appwriteImage")
fun ImageView.loadAppwriteImage(imageId: String?) {
    imageId ?: return

    findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
        val url =
            "${Configuration.ENDPOINT}/storage/files/${imageId}/view?project=${Configuration.PROJECT_ID}"

        Glide
            .with(this@loadAppwriteImage)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this@loadAppwriteImage)
    }
}
