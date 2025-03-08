package com.meronacompany.feature.home

import com.meronacompany.common.base.BaseEvent
import com.meronacompany.domain.model.ResponseGenreData
import com.meronacompany.domain.model.ResponsePopularData

sealed class HomeEvent : BaseEvent {

    data class PopularMoviesEvent(val popularMovies: ResponsePopularData) : HomeEvent()

    data class GenresMoviesEvent(val genres: ResponseGenreData) : HomeEvent()

    data class ErrorEvent(val errorMessage: String) : HomeEvent()
}
