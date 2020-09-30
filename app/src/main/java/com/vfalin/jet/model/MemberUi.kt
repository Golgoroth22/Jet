package com.vfalin.jet.model

data class MemberUi(
    val id: String = "",
    val status: String = "",
    val name: String? = "",
    val utcOffset: Double? = 0.0,
    val username: String? = ""
)