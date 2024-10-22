package com.example.recipefinder.utils.imageLoader

import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

class GlideImageLoader @Inject constructor(
) : ImageLoader {
    override fun loadImage(url: String?, imageView: ImageView) {
        Glide.with(imageView)
            .load(url)
            .centerCrop()
            .into(imageView)
    }
}

