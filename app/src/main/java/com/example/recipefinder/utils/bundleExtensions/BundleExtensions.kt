package com.example.recipefinder.utils.bundleExtensions

import android.os.Build
import android.os.Bundle
import java.io.Serializable

inline fun <reified T : Serializable> Bundle?.getSerializableCompat(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this?.getSerializable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        this?.getSerializable(key) as? T
    }
}