package com.example.recipefinder.utils.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager

open class PagingDataDelegationAdapter<T : Any>(
    diffCallback: DiffUtil.ItemCallback<T>,
    vararg delegates: AdapterDelegate<List<T>>
) : PagingDataAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    protected val delegatesManager: AdapterDelegatesManager<List<T>> = AdapterDelegatesManager()

    init {
        delegates.forEach { delegatesManager.addDelegate(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)
        delegatesManager.onBindViewHolder(currentList(), position, holder, emptyList<T>())
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        return delegatesManager.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        delegatesManager.onViewDetachedFromWindow(holder)
    }

    private fun currentList(): List<T> {
        return snapshot().toList().filterNotNull()
    }
}