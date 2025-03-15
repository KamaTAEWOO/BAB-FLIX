package com.meronacompany.feature.home

import com.meronacompany.common.base.BaseState
import com.meronacompany.domain.model.PopularMovieData
import com.meronacompany.domain.model.PopularTvData
import com.meronacompany.domain.model.ResponseGenreData
import com.meronacompany.domain.model.ResponseMovieCertificationData
import com.meronacompany.domain.model.ResponseMovieCreditsData
import com.meronacompany.domain.model.ResponseMovieDetailData
import com.meronacompany.domain.model.ResponseMovieVideo
import com.meronacompany.domain.model.ResponsePopularMovieData
import com.meronacompany.domain.model.ResponsePopularTvData
import com.meronacompany.domain.model.ResponseTvDetailData

data class HomeState(
    // movie
    val popularMovies: ResponsePopularMovieData? = null,
    val allPopularMoviesData: Map<Int, List<PopularMovieData>> = emptyMap(),
    val genresMovies: ResponseGenreData? = null,
    val movieVideo: ResponseMovieVideo? = null,
//    val movieVideoKey: String? = null,
    val movieDetail: ResponseMovieDetailData? = null,
    val movieCredits: ResponseMovieCreditsData? = null,
    val movieCertification: ResponseMovieCertificationData? = null,

    // tv
    val popularTVs: ResponsePopularTvData? = null,
    val allPopularTVsData: Map<Int, List<PopularTvData>> = emptyMap(),
    val genresTVs: ResponseGenreData? = null,
    val tvDetail: ResponseTvDetailData? = null,
    val tvVideo: ResponseMovieVideo? = null,
//    val tvVideoKey: String? = null,

    // error
    val errorMessage: String? = null
) : BaseState
