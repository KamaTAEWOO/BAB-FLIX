package com.meronacompany.domain.model

data class ResponsePopularTvData(
    val page: Int,
    val results: List<PopularTvData>
)

data class PopularTvData(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val first_air_date: String,
    val name: String,
    val vote_average: Double,
    val vote_count: Int
)
