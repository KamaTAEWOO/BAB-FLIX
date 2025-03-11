package com.meronacompany.core.model.dto

import com.meronacompany.domain.model.Cast
import com.meronacompany.domain.model.Crew
import com.meronacompany.domain.model.ResponseMovieCreditsData

data class ResponseMovieCreditsDto(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
) {
    fun toModel() = ResponseMovieCreditsData(
        id = id,
        cast = cast,
        crew = crew
    )
}
