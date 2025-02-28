package com.meronacompany.core.network.service

import com.meronacompany.core.model.dto.CommonDto
import retrofit2.http.GET
import retrofit2.http.Header
import kotlin.coroutines.coroutineContext

interface HomeService {

    @GET("authentication")
    suspend fun requestIsApiKey(): CommonDto

}