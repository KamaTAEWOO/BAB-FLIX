package com.meronacompany.core.model.dto

import com.meronacompany.domain.model.CountryCertification
import com.meronacompany.domain.model.ResponseMovieCertificationData

data class ResponseMovieCertificationDto(
    val id: Int,
    val results: List<CountryCertification>
) {
    fun toModel() = ResponseMovieCertificationData(
        id = id,
        results = results
    )
}