package com.meronacompany.core.model.dto

import com.meronacompany.domain.model.ResponseMovieVideo
import com.meronacompany.domain.model.VideoResult

data class ResponseMovieVideoDto(
    val id: Int,
    val results: List<VideoResult>
) {
    fun toModel() = ResponseMovieVideo(
        id = id,
        results = results
    )
}
