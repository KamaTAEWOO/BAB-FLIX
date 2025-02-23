package com.meronacompany.core.model.dto

import com.meronacompany.domain.model.CommonData

data class CommonDto(
    val success: Boolean,
) {
    fun toModel() = CommonData(
        success = success,
    )
}
