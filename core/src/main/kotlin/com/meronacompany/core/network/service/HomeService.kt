package com.meronacompany.core.network.service

import com.meronacompany.core.model.dto.CommonDto
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeService {

    @GET("authentication")
    suspend fun requestIsApiKey(
        @Header("Accept") accept: String = "application/json",
        @Header("Authorization") authorization: String = "Bearer "
    ): CommonDto

}