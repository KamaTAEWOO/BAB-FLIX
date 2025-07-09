package com.meronacompany.domain.repository

import com.meronacompany.domain.model.ResponseGenreData
import com.meronacompany.domain.model.ResponseMovieCertificationData
import com.meronacompany.domain.model.ResponseMovieCreditsData
import com.meronacompany.domain.model.ResponseMovieDetailData
import com.meronacompany.domain.model.ResponseMovieVideo
import com.meronacompany.domain.model.ResponsePopularMovieData
import com.meronacompany.domain.model.ResponsePopularTvData
import com.meronacompany.domain.model.ResponseTvDetailData
import com.meronacompany.domain.model.ResponseWatchProvidersData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun requestIsApiKey(): Flow<Boolean>

    fun requestPopularMovies(pageNumber: Int): Flow<ResponsePopularMovieData>

    fun requestPopularTVs(pageNumber: Int): Flow<ResponsePopularTvData>

    fun requestWatchProviders(movieId: Int): Flow<ResponseWatchProvidersData>

    fun requestMovieGenres(): Flow<ResponseGenreData>

    fun requestTVGenres(): Flow<ResponseGenreData>

    fun requestMovieVideo(id: Int): Flow<ResponseMovieVideo>

    fun requestTvVideo(id: Int): Flow<ResponseMovieVideo>

    fun requestMovieDetail(movieId: Int): Flow<ResponseMovieDetailData>

    fun requestTvDetail(tvId: Int): Flow<ResponseTvDetailData>

    fun requestMovieCertification(movieId: Int): Flow<ResponseMovieCertificationData>

    fun requestMovieCredits(movieId: Int): Flow<ResponseMovieCreditsData>

    fun requestTvCredits(tvId: Int): Flow<ResponseMovieCreditsData>

    fun getApiCallCount(): Int

    fun setLanguage(language: String)

    fun getLanguage(): String
}