package com.vfalin.jet.utils

import android.content.Context

/**
 * Utility class for work with Shared Preferences.
 *
 */
class PreferencesHelper(context: Context) {
    var offset: Int by DelegatedPreferences(context, USER_DATA, MEMBERS_OFFSET, 0)

    companion object {
        private const val USER_DATA = "USER_DATA"

        private const val MEMBERS_OFFSET = "MEMBERS_OFFSET"
    }
}