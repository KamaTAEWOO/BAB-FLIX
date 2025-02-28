package com.meronacompany.core.network.service

import com.meronacompany.core.model.dto.CommonDto
import com.meronacompany.core.model.dto.ResponseLanguagesDto
import retrofit2.http.GET

interface HomeService {

    @GET("authentication")
    suspend fun requestIsApiKey(): CommonDto

    @GET("configuration/primary_translations")
    suspend fun requestGetLanguages(): ResponseLanguagesDto

}