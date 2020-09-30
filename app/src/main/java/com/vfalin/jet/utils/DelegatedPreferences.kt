package com.vfalin.jet.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KProperty

/**
 * Utility class for work with Shared Preferences.
 *
 * @param context application context
 * @param sPrefName name of selected Shared Preference
 * @param KEY key of selected Shared Preference
 * @param defaultValue default value of selected Shared Preference
 */
class DelegatedPreferences<T>(
    context: Context,
    sPrefName: String,
    private val KEY: String,
    private val defaultValue: T
) {

    private val sPref: SharedPreferences by lazy {
        context.getSharedPreferences(sPrefName, Context.MODE_PRIVATE)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreferences(KEY, defaultValue)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        savePreference(KEY, value)
    }

    @Suppress("UNCHECKED_CAST")
    private fun findPreferences(key: String, defaultValue: T): T {
        with(sPref) {
            val result: Any = when (defaultValue) {
                is Int -> getInt(key, defaultValue)
                is Long -> getLong(key, defaultValue)
                is Float -> getFloat(key, defaultValue)
                is String -> getString(key, defaultValue) ?: ""
                is Boolean -> getBoolean(key, defaultValue)
                else -> throw IllegalArgumentException()
            }
            return result as T
        }
    }

    private fun savePreference(key: String, value: T) {
        with(sPref.edit()) {
            when (value) {
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                else -> throw IllegalArgumentException()
            }.apply()
        }
    }
}