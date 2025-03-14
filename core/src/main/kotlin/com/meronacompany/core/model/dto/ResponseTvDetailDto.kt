package com.meronacompany.core.model.dto

import com.meronacompany.domain.model.Creator
import com.meronacompany.domain.model.Episode
import com.meronacompany.domain.model.Genre
import com.meronacompany.domain.model.Network
import com.meronacompany.domain.model.ProductionCompany
import com.meronacompany.domain.model.ProductionCountry
import com.meronacompany.domain.model.ResponseTvDetailData
import com.meronacompany.domain.model.Season
import com.meronacompany.domain.model.SpokenLanguage

data class ResponseTvDetailDto(
    val adult: Boolean,
    val backdrop_path: String?,
    val created_by: List<Creator>,
    val episode_run_time: List<Int>,
    val first_air_date:String?,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String?,
    val last_episode_to_air: Episode?,
    val name: String,
    val next_episode_to_air: Episode?,
    val networks: List<Network>,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val seasons: List<Season>,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val type: String,
    val vote_average: Double,
    val vote_count: Int
) {
    fun toModel() = ResponseTvDetailData(
        adult = adult,
        backdrop_path = backdrop_path,
        created_by = created_by,
        episode_run_time = episode_run_time,
        first_air_date = first_air_date,
        genres = genres,
        homepage = homepage,
        id = id,
        in_production = in_production,
        languages = languages,
        last_air_date = last_air_date,
        last_episode_to_air = last_episode_to_air,
        name = name,
        next_episode_to_air = next_episode_to_air,
        networks = networks,
        number_of_episodes = number_of_episodes,
        number_of_seasons = number_of_seasons,
        origin_country = origin_country,
        original_language = original_language,
        original_name = original_name,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        production_companies = production_companies,
        production_countries = production_countries,
        seasons = seasons,
        spoken_languages = spoken_languages,
        status = status,
        tagline = tagline,
        type = type,
        vote_average = vote_average,
        vote_count = vote_count
    )
}
