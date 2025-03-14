package com.meronacompany.feature.home

import com.meronacompany.common.base.BaseEvent
import com.meronacompany.domain.model.ResponseGenreData
import com.meronacompany.domain.model.ResponseMovieCertificationData
import com.meronacompany.domain.model.ResponseMovieCreditsData
import com.meronacompany.domain.model.ResponseMovieDetailData
import com.meronacompany.domain.model.ResponseMovieVideo
import com.meronacompany.domain.model.ResponsePopularMovieData

sealed class HomeEvent : BaseEvent {

    // movie
    data class PopularMoviesEvent(val popularMovies: ResponsePopularMovieData) : HomeEvent()

    data class GenresMoviesEvent(val genres: ResponseGenreData) : HomeEvent()

    data class MovieVideoEvent(val movieVideo: ResponseMovieVideo) : HomeEvent()

    data class MovieDetailEvent(val movieDetail: ResponseMovieDetailData) : HomeEvent()

    data class MovieCreditsEvent(val movieCredits: ResponseMovieCreditsData) : HomeEvent()

    data class MovieCertificationEvent(val movieCertification: ResponseMovieCertificationData) : HomeEvent()

    // tv
    data class PopularTVsEvent(val popularTVs: ResponsePopularMovieData) : HomeEvent()

    data class GenresTVsEvent(val genres: ResponseGenreData) : HomeEvent()

    // error
    data class ErrorEvent(val errorMessage: String) : HomeEvent()
}
