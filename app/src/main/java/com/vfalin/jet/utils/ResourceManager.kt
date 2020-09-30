package com.vfalin.jet.utils

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import javax.inject.Inject

class ResourceManager @Inject constructor(private val context: Context) {

    fun getString(@StringRes resId: Int): String = context.getString(resId)

    fun getString(id: Int, vararg formatArgs: Any) =
        String.format(context.getString(id, *formatArgs))

    fun getDrawable(@DrawableRes resId: Int) = ContextCompat.getDrawable(context, resId)

    fun getColor(@ColorRes resId: Int) = ContextCompat.getColor(context, resId)

    fun getColorArray(@ArrayRes resId: Int): IntArray {
        val ta = context.resources.obtainTypedArray(resId)
        val colors = IntArray(ta.length())
        for (i in 0 until ta.length())
            colors[i] = ta.getColor(i, 0)

        ta.recycle()
        return colors
    }
}
