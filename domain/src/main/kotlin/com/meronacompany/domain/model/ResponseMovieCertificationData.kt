package com.meronacompany.domain.model

data class ResponseMovieCertificationData(
    val id: Int,
    val results: List<CountryCertification>
)

data class CountryCertification(
    val iso_3166_1: String,
    val release_dates: List<ReleaseDateInfo>
)

data class ReleaseDateInfo(
    val certification: String?,
    val descriptors: List<String>?,
    val iso_639_1: String?,
    val note: String?,
    val release_date: String,
    val type: Int
)