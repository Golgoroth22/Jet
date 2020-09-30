package com.vfalin.jet.interactors

interface OffsetStorage {
    fun getOffset(): Int

    fun increaseOffset()
}