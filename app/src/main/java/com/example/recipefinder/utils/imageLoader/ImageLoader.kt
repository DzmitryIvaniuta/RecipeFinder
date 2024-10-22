package com.example.recipefinder.utils.imageLoader

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(url: String?, imageView: ImageView)
}
