package com.vfalin.jet.network.pojo

import com.squareup.moshi.Json
import com.vfalin.jet.model.MemberUi

data class MemberResponse(
    @Json(name = "_id") val id: String,
    @Json(name = "status") val status: String,
    @Json(name = "name") val name: String,
    @Json(name = "utcOffset") val utcOffset: Double?,
    @Json(name = "username") val username: String?
) {
    fun convertTo() = MemberUi(id, status, name, utcOffset, username)
}