package com.meronacompany.core.network.service

import com.meronacompany.core.model.dto.CommonDto
import retrofit2.http.GET

interface HomeService {

    @GET("authentication")
    suspend fun requestIsApiKey(): CommonDto

}