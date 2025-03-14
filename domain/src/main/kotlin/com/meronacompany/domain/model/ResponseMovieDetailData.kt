package com.meronacompany.domain.model

data class ResponseMovieDetailData(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val tagline: String,
    val releaseDate: String,
    val runtime: Int,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val status: String,
    val posterPath: String?,
    val backdropPath: String?,
    val homepage: String?,
    val genres: List<Genre>,
    val productionCompanies: List<ProductionCompany>,
    val spokenLanguages: List<SpokenLanguage>
)
