package com.vfalin.jet.utils

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Utility file for contain extensions.
 */

/**
 * This [Context] extension method can be called for simple start new activity.
 *
 * @param T activity class
 */
inline fun <reified T> Context.launchActivity() {
    startActivity(Intent(this, T::class.java))
}

/**
 * This [FragmentActivity] extension method can be called for create [ViewModel].
 *
 * @param factory factory for creating [ViewModel]
 *
 * @return new [ViewModel] object
 */
inline fun <reified T : ViewModel> FragmentActivity.injectViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, factory)[T::class.java]
}
