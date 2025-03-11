package com.meronacompany.domain.repository

import com.meronacompany.domain.model.ResponseGenreData
import com.meronacompany.domain.model.ResponseMovieDetailData
import com.meronacompany.domain.model.ResponseMovieVideo
import com.meronacompany.domain.model.ResponsePopularData
import com.meronacompany.domain.model.ResponseWatchProvidersData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun requestIsApiKey(): Flow<Boolean>

    fun requestPopularMovies(pageNumber: Int): Flow<ResponsePopularData>

    fun requestPopularTVs(pageNumber: Int): Flow<ResponsePopularData>

    fun requestWatchProviders(movieId: Int): Flow<ResponseWatchProvidersData>

    fun requestMovieGenres(): Flow<ResponseGenreData>

    fun requestTVGenres(): Flow<ResponseGenreData>

    fun requestMovieVideo(movieId: Int): Flow<ResponseMovieVideo>

    fun requestMovieDetail(movieId: Int): Flow<ResponseMovieDetailData>
}