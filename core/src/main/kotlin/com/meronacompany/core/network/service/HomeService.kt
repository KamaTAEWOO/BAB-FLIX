package com.meronacompany.core.network.service

import androidx.compose.ui.text.intl.Locale
import com.meronacompany.core.utility.Locales
import com.meronacompany.core.model.dto.CommonDto
import com.meronacompany.core.model.dto.ResponsePopularMovieDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    @GET("authentication")
    suspend fun requestIsApiKey(): CommonDto

    @GET("movie/popular")
    suspend fun requestPopularMovies(
        @Query("language") language: String = Locales.KO_KR,
        @Query("page") page: Int
    ): ResponsePopularMovieDto

}