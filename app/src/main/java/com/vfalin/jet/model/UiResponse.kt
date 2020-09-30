package com.vfalin.jet.model

data class UiResponse<T>(
    val data: T? = null,
    val isLoading: Boolean,
    val error: Throwable? = null
)