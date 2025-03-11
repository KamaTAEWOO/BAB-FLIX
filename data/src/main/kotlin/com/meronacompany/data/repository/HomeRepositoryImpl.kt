package com.meronacompany.data.repository

import com.meronacompany.core.network.service.HomeService
import com.meronacompany.domain.model.ResponseGenreData
import com.meronacompany.domain.model.ResponseMovieDetailData
import com.meronacompany.domain.model.ResponseMovieVideo
import com.meronacompany.domain.model.ResponsePopularData
import com.meronacompany.domain.model.ResponseWatchProvidersData
import com.meronacompany.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeRepositoryImpl(
    private val homeService: HomeService
) : HomeRepository {

    override fun requestIsApiKey(): Flow<Boolean> = flow {
        emit(homeService.requestIsApiKey().toModel().success)
    }

    override fun requestPopularMovies(pageNumber: Int): Flow<ResponsePopularData> = flow {
        emit(homeService.requestPopularMovies(page = pageNumber).toModel())
    }

    override fun requestPopularTVs(pageNumber: Int): Flow<ResponsePopularData> = flow {
        emit(homeService.requestPopularTVs(page = pageNumber).toModel())
    }

    override fun requestWatchProviders(movieId: Int): Flow<ResponseWatchProvidersData> = flow {
        emit(homeService.requestWatchProviders(movieId = movieId).toModel())
    }

    override fun requestMovieGenres(): Flow<ResponseGenreData> = flow {
        emit(homeService.requestMovieGenres().toModel())
    }

    override fun requestTVGenres(): Flow<ResponseGenreData> = flow {
        emit(homeService.requestTVGenres().toModel())
    }

    override fun requestMovieVideo(movieId: Int): Flow<ResponseMovieVideo> = flow {
        emit(homeService.requestMovieVideo(movieId = movieId).toModel())
    }

    override fun requestMovieDetail(movieId: Int): Flow<ResponseMovieDetailData> = flow {
        emit(homeService.requestMovieDetail(movieId = movieId).toModel())
    }

}