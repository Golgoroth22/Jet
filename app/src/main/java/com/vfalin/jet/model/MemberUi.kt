package com.vfalin.jet.model

data class MemberUi(
    val id: String,
    val status: String,
    val name: String,
    val utcOffset: Double?,
    val username: String?
)