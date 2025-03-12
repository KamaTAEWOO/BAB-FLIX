package com.meronacompany.feature.home

import com.meronacompany.common.base.BaseEvent
import com.meronacompany.domain.model.ResponseGenreData
import com.meronacompany.domain.model.ResponseMovieCertificationData
import com.meronacompany.domain.model.ResponseMovieCreditsData
import com.meronacompany.domain.model.ResponseMovieDetailData
import com.meronacompany.domain.model.ResponseMovieVideo
import com.meronacompany.domain.model.ResponsePopularData

sealed class HomeEvent : BaseEvent {

    data class PopularMoviesEvent(val popularMovies: ResponsePopularData) : HomeEvent()

    data class GenresMoviesEvent(val genres: ResponseGenreData) : HomeEvent()

    data class MovieVideoEvent(val movieVideo: ResponseMovieVideo) : HomeEvent()

    data class MovieDetailEvent(val movieDetail: ResponseMovieDetailData) : HomeEvent()

    data class MovieCreditsEvent(val movieCredits: ResponseMovieCreditsData) : HomeEvent()

    data class MovieCertificationEvent(val movieCertification: ResponseMovieCertificationData) : HomeEvent()

    data class ErrorEvent(val errorMessage: String) : HomeEvent()
}
