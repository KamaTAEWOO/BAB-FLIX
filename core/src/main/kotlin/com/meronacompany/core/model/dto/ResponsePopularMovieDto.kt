package com.meronacompany.core.model.dto

import com.meronacompany.domain.model.Movie
import com.meronacompany.domain.model.ResponsePopularMovieData

data class ResponsePopularMovieDto(
    val page: Int,
    val results: List<Movie>
) {
    fun toModel(): ResponsePopularMovieData {
        return ResponsePopularMovieData(
            page = page,
            results = results
        )
    }
}
