package com.meronacompany.data.repository

import com.meronacompany.core.network.service.HomeService
import com.meronacompany.domain.model.ResponsePopularMovieData
import com.meronacompany.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val homeService: HomeService
) : HomeRepository {

    override fun requestIsApiKey(): Flow<Boolean> = flow {
        emit(homeService.requestIsApiKey().toModel().success)
    }

    override fun requestPopularMovies(pageNumber: Int): Flow<ResponsePopularMovieData> = flow {
        emit(homeService.requestPopularMovies(page = pageNumber).toModel())
    }

}