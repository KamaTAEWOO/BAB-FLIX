package com.meronacompany.domain.repository

import com.meronacompany.domain.model.ResponsePopularData
import com.meronacompany.domain.model.ResponseWatchProvidersData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun requestIsApiKey(): Flow<Boolean>

    fun requestPopularMovies(pageNumber: Int): Flow<ResponsePopularData>

    fun requestPopularTVs(pageNumber: Int): Flow<ResponsePopularData>

    fun requestWatchProviders(movieId: Int): Flow<ResponseWatchProvidersData>
}