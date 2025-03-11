package com.meronacompany.feature.home

import com.meronacompany.common.base.BaseState
import com.meronacompany.domain.model.Movie
import com.meronacompany.domain.model.ResponseGenreData
import com.meronacompany.domain.model.ResponseMovieCreditsData
import com.meronacompany.domain.model.ResponseMovieDetailData
import com.meronacompany.domain.model.ResponseMovieVideo
import com.meronacompany.domain.model.ResponsePopularData

data class HomeState(
    val popularMovies: ResponsePopularData? = null,
    val allPopularMoviesData: Map<Int, List<Movie>> = emptyMap(),
    val genresMovies: ResponseGenreData? = null,
    val movieVideo: ResponseMovieVideo? = null,
    val movieVideoKey: String? = null,
    val movieDetail: ResponseMovieDetailData? = null,
    val movieCredits: ResponseMovieCreditsData? = null,
    val errorMessage: String? = null
) : BaseState
