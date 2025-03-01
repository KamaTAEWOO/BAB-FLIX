package com.meronacompany.domain.repository

import com.meronacompany.domain.model.ResponsePopularMovieData
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun requestIsApiKey(): Flow<Boolean>

    fun requestPopularMovies(pageNumber: Int): Flow<ResponsePopularMovieData>
}