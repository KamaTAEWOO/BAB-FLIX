package com.meronacompany.core.model.dto

import com.meronacompany.domain.model.PopularMovieData
import com.meronacompany.domain.model.ResponsePopularMovieData

data class ResponsePopularMovieDto(
    val page: Int,
    val results: List<PopularMovieData>
) {
    fun toModel(): ResponsePopularMovieData {
        return ResponsePopularMovieData(
            page = page,
            results = results
        )
    }
}
