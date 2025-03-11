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

data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCompany(
    val id: Int,
    val name: String,
    val logoPath: String?,
    val originCountry: String
)

data class SpokenLanguage(
    val iso6391: String,
    val name: String
)