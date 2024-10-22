package com.example.recipefinder.utils.viewBinding.fragmentViewBinding

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingProperty<T : ViewBinding>(
    private val binder: (View) -> T,
) : ReadOnlyProperty<Fragment, T> {

    private var viewBinding: T? = null
    private val lifecycleObserver = BindingLifecycleObserver()

    @MainThread
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        this.viewBinding?.let { return it }

        val view = thisRef.requireView()
        thisRef.viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        return binder(view).also { vb -> this.viewBinding = vb }
    }

    private inner class BindingLifecycleObserver : DefaultLifecycleObserver {

        private val mainHandler = Handler(Looper.getMainLooper())

        @MainThread
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            mainHandler.post { viewBinding = null }
        }
    }
}

inline fun <reified T : ViewBinding> Fragment.viewBinding(
    noinline binder: (View) -> T
): ReadOnlyProperty<Fragment, T> {
    return FragmentViewBindingProperty(binder)
}
