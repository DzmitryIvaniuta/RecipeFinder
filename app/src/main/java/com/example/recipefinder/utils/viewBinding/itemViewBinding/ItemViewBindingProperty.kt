package com.example.recipefinder.utils.viewBinding.itemViewBinding

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ItemViewBindingProperty<T : ViewBinding>(
    private val binder: (View) -> T
) : ReadOnlyProperty<RecyclerView.ViewHolder, T> {

    private var viewBinding: T? = null

    override fun getValue(thisRef: RecyclerView.ViewHolder, property: KProperty<*>): T {
        return viewBinding ?: binder(thisRef.itemView).also { viewBinding = it }
    }
}

inline fun <reified T : ViewBinding> RecyclerView.ViewHolder.viewBinding(
    noinline binder: (View) -> T
): ReadOnlyProperty<RecyclerView.ViewHolder, T> {
    return ItemViewBindingProperty(binder)
}
