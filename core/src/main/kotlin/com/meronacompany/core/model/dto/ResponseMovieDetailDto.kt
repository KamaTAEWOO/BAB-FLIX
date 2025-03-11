package com.meronacompany.core.model.dto

import com.meronacompany.domain.model.Genre
import com.meronacompany.domain.model.ProductionCompany
import com.meronacompany.domain.model.ResponseMovieDetailData
import com.meronacompany.domain.model.SpokenLanguage

data class ResponseMovieDetailDto(
    val id: Int,
    val title: String,
    val original_title: String,
    val overview: String,
    val tagline: String,
    val release_date: String,
    val runtime: Int,
    val vote_average: Double,
    val vote_count: Int,
    val popularity: Double,
    val status: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val homepage: String?,
    val genres: List<Genre>,
    val production_companies: List<ProductionCompany>,
    val spoken_languages: List<SpokenLanguage>
) {
    fun toModel() = ResponseMovieDetailData(
        id = id,
        title = title,
        originalTitle = original_title,
        overview = overview,
        tagline = tagline,
        releaseDate = release_date,
        runtime = runtime,
        voteAverage = vote_average,
        voteCount = vote_count,
        popularity = popularity,
        status = status,
        posterPath = poster_path,
        backdropPath = backdrop_path,
        homepage = homepage,
        genres = genres,
        productionCompanies = production_companies,
        spokenLanguages = spoken_languages
    )
}