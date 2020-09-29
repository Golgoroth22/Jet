package com.vfalin.jet.network.pojo

import com.squareup.moshi.Json

data class MembersResponse(
    @Json(name = "members") val members: List<MemberResponse>,
    @Json(name = "count") val count: Int,
    @Json(name = "offset") val offset: Int,
    @Json(name = "total") val total: Long,
    @Json(name = "success") val isSuccess: Boolean
)