package com.vfalin.jet.interactors

import com.vfalin.jet.utils.PreferencesHelper

class OffsetStorageImpl(private val preferences: PreferencesHelper) : OffsetStorage {

    override fun getOffset() = preferences.offset

    override fun increaseOffset() {
        preferences.offset = getOffset() + 1
    }
}