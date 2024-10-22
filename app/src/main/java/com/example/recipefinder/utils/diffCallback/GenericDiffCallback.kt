package com.example.recipefinder.utils.diffCallback

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.recipefinder.core.domain.model.IModel

class GenericDiffCallback<T : IModel> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.javaClass == newItem.javaClass
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}