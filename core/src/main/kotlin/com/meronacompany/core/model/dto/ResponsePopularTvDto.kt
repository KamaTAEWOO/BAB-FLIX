package com.meronacompany.core.model.dto

import com.meronacompany.domain.model.PopularTvData
import com.meronacompany.domain.model.ResponsePopularTvData

data class ResponsePopularTvDto(
    val page: Int,
    val results: List<PopularTvData>
) {
    fun toModel(): ResponsePopularTvData {
        return ResponsePopularTvData(
            page = page,
            results = results
        )
    }
}