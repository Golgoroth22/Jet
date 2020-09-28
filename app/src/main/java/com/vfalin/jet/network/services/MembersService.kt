package com.vfalin.jet.network.services

import com.vfalin.jet.network.pojo.MembersResponse
import retrofit2.http.*

interface MembersService {
    @GET("/api/v1/channels.members?roomName=general")
    suspend fun getMembers(
        @Header("X-User-Id") userId: String,
        @Header("X-Auth-Token") token: String,
    ): MembersResponse
}