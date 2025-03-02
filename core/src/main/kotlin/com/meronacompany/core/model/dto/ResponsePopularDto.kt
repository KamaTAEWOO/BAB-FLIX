package com.meronacompany.core.model.dto

import com.meronacompany.domain.model.Movie
import com.meronacompany.domain.model.ResponsePopularData

data class ResponsePopularDto(
    val page: Int,
    val results: List<Movie>
) {
    fun toModel(): ResponsePopularData {
        return ResponsePopularData(
            page = page,
            results = results
        )
    }
}
