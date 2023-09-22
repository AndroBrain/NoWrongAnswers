package com.androbrain.core.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigateSafely(navDirections: NavDirections) {
    try {
        navigate(navDirections)
    } catch (e: IllegalArgumentException) {
        Log.e("NavigationError", e.toString())
    }
}
