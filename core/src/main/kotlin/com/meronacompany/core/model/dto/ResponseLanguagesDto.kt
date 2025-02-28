package com.meronacompany.core.model.dto

import com.meronacompany.domain.model.RequestLanguagesData

data class ResponseLanguagesDto(
    val languages: String
) {
    fun toModel() = RequestLanguagesData(
        languages = languages,
    )
}
