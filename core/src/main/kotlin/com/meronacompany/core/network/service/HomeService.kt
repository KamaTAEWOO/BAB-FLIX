package com.meronacompany.core.network.service

import com.meronacompany.core.model.dto.CommonDto
import com.meronacompany.core.model.dto.ResponseGenreDto
import com.meronacompany.core.model.dto.ResponseMovieCertificationDto
import com.meronacompany.core.model.dto.ResponseMovieCreditsDto
import com.meronacompany.core.model.dto.ResponseMovieDetailDto
import com.meronacompany.core.model.dto.ResponseMovieVideoDto
import com.meronacompany.core.model.dto.ResponsePopularMovieDto
import com.meronacompany.core.model.dto.ResponsePopularTvDto
import com.meronacompany.core.model.dto.ResponseTvDetailDto
import com.meronacompany.core.model.dto.ResponseWatchProvidersDto
import com.meronacompany.core.utility.Locales
import com.meronacompany.domain.model.ResponseTvDetailData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {

    @GET("authentication")
    suspend fun requestIsApiKey(): CommonDto

    @GET("movie/popular")
    suspend fun requestPopularMovies(
        @Query("language") language: String = Locales.KO_KR,
        @Query("page") page: Int
    ): ResponsePopularMovieDto

    @GET("tv/top_rated")
    suspend fun requestPopularTVs(
        @Query("language") language: String = Locales.KO_KR,
        @Query("page") page: Int
    ): ResponsePopularTvDto

    @GET("movie/{movie_id}/watch/providers")
    suspend fun requestWatchProviders(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = Locales.KO_KR
    ): ResponseWatchProvidersDto

    @GET("genre/movie/list")
    suspend fun requestMovieGenres(
        @Query("language") language: String = Locales.KO_KR
    ): ResponseGenreDto

    @GET("genre/tv/list")
    suspend fun requestTVGenres(
        @Query("language") language: String = Locales.KO_KR
    ): ResponseGenreDto

    @GET("movie/{id}/videos")
    suspend fun requestMovieVideo(
        @Path("id") id: Int,
        @Query("language") language: String = Locales.EN_US
    ): ResponseMovieVideoDto

    @GET("tv/{id}/videos")
    suspend fun requestTvVideo(
        @Path("id") id: Int,
        @Query("language") language: String = Locales.EN_US
    ): ResponseMovieVideoDto

    @GET("movie/{movie_id}")
    suspend fun requestMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = Locales.KO_KR
    ): ResponseMovieDetailDto

    @GET("tv/{tv_id}")
    suspend fun requestTvDetail(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String = Locales.KO_KR
    ): ResponseTvDetailDto

    @GET("movie/{movie_id}/release_dates")
    suspend fun requestMovieCertification(
        @Path("movie_id") movieId: Int
    ): ResponseMovieCertificationDto

    @GET("movie/{movie_id}/credits")
    suspend fun requestMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = Locales.KO_KR
    ): ResponseMovieCreditsDto

    @GET("tv/{tv_id}/credits")
    suspend fun requestTvCredits(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String = Locales.KO_KR
    ): ResponseMovieCreditsDto

}