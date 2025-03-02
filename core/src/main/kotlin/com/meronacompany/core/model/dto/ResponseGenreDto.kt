package com.meronacompany.core.model.dto

import com.meronacompany.domain.model.GenreData
import com.meronacompany.domain.model.ResponseGenreData

data class ResponseGenreDto(
    val genres: List<GenreData>
) {
    fun toModel(): ResponseGenreData {
        return ResponseGenreData(
            genres = genres
        )
    }
}