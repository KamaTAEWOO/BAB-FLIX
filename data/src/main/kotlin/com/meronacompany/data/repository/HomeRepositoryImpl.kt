package com.meronacompany.data.repository

import android.content.Context
import com.meronacompany.core.local.PreferenceManager
import com.meronacompany.core.network.service.HomeService
import com.meronacompany.domain.model.ResponseGenreData
import com.meronacompany.domain.model.ResponseMovieCertificationData
import com.meronacompany.domain.model.ResponseMovieCreditsData
import com.meronacompany.domain.model.ResponseMovieDetailData
import com.meronacompany.domain.model.ResponseMovieVideo
import com.meronacompany.domain.model.ResponsePopularMovieData
import com.meronacompany.domain.model.ResponsePopularTvData
import com.meronacompany.domain.model.ResponseTvDetailData
import com.meronacompany.domain.model.ResponseWatchProvidersData
import com.meronacompany.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class HomeRepositoryImpl(
    private val context: Context,
    private val homeService: HomeService
) : HomeRepository {

    override fun requestIsApiKey(): Flow<Boolean> = flow {
        emit(homeService.requestIsApiKey().toModel().success)
        plusCount()
    }

    override fun requestPopularMovies(pageNumber: Int): Flow<ResponsePopularMovieData> = flow {
        emit(homeService.requestPopularMovies(page = pageNumber).toModel())
        plusCount()
    }

    override fun requestPopularTVs(pageNumber: Int): Flow<ResponsePopularTvData> = flow {
        emit(homeService.requestPopularTVs(page = pageNumber).toModel())
        plusCount()
    }

    override fun requestWatchProviders(movieId: Int): Flow<ResponseWatchProvidersData> = flow {
        emit(homeService.requestWatchProviders(movieId = movieId).toModel())
        plusCount()
    }

    override fun requestMovieGenres(): Flow<ResponseGenreData> = flow {
        emit(homeService.requestMovieGenres().toModel())
        plusCount()
    }

    override fun requestTVGenres(): Flow<ResponseGenreData> = flow {
        emit(homeService.requestTVGenres().toModel())
        plusCount()
    }

    override fun requestMovieVideo(id: Int): Flow<ResponseMovieVideo> = flow {
        emit(homeService.requestMovieVideo(id = id).toModel())
        plusCount()
    }

    override fun requestTvVideo(id: Int): Flow<ResponseMovieVideo> = flow {
        emit(homeService.requestTvVideo(id = id).toModel())
        plusCount()
    }

    override fun requestMovieDetail(movieId: Int): Flow<ResponseMovieDetailData> = flow {
        emit(homeService.requestMovieDetail(movieId = movieId).toModel())
        plusCount()
    }

    override fun requestTvDetail(tvId: Int): Flow<ResponseTvDetailData> = flow {
        emit(homeService.requestTvDetail(tvId = tvId).toModel())
        plusCount()
    }

    override fun requestMovieCertification(movieId: Int): Flow<ResponseMovieCertificationData> = flow {
        emit(homeService.requestMovieCertification(movieId = movieId).toModel())
        plusCount()
    }

    override fun requestMovieCredits(movieId: Int): Flow<ResponseMovieCreditsData> = flow {
        emit(homeService.requestMovieCredits(movieId = movieId).toModel())
        plusCount()
    }

    override fun requestTvCredits(tvId: Int): Flow<ResponseMovieCreditsData> = flow {
        emit(homeService.requestTvCredits(tvId = tvId).toModel())
        plusCount()
    }

    override fun getApiCallCount(): Int = PreferenceManager.getApiCallCount(context)

    // api count 함수
    private fun plusCount() {
        val count = PreferenceManager.getApiCallCount(context)
        Timber.d("taewoo - Retrofit 생성 시 API 호출 횟수: $count")
        PreferenceManager.incrementAPICallCount(context)
    }
}