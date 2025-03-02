package com.meronacompany.core.network.service

import com.meronacompany.core.model.dto.CommonDto
import com.meronacompany.core.model.dto.ResponsePopularDto
import com.meronacompany.core.utility.Locales
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    @GET("authentication")
    suspend fun requestIsApiKey(): CommonDto

    @GET("movie/popular")
    suspend fun requestPopularMovies(
        @Query("language") language: String = Locales.KO_KR,
        @Query("page") page: Int
    ): ResponsePopularDto

    @GET("tv/popular")
    suspend fun requestPopularTVs(
        @Query("language") language: String = Locales.KO_KR,
        @Query("page") page: Int
    ): ResponsePopularDto

}