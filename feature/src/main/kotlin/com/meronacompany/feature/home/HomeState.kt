package com.meronacompany.feature.home

import com.meronacompany.common.base.BaseState
import com.meronacompany.domain.model.ResponsePopularData

data class HomeState(
    val popularMovies: ResponsePopularData? = null,
    val errorMessage: String? = null
) : BaseState
