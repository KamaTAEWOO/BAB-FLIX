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
    val adult_: Boolean,
    val backdrop_path_: String?,
    val created_by_: List<Creator>,
    val episode_run_time_: List<Int>,
    val first_air_date_: String?,
    val genres_: List<Genre>,
    val homepage_: String?,
    val id_: Int,
    val in_production_: Boolean,
    val languages_: List<String>,
    val last_air_date_: String?,
    val last_episode_to_air_: Episode?,
    val name_: String,
    val next_episode_to_air_: Episode?,
    val networks_: List<Network>,
    val number_of_episodes_: Int,
    val number_of_seasons_: Int,
    val origin_country_: List<String>,
    val original_language_: String,
    val original_name_: String,
    val overview_: String,
    val popularity_: Double,
    val poster_path_: String?,
    val production_companies_: List<ProductionCompany>,
    val production_countries_: List<ProductionCountry>,
    val seasons_: List<Season>,
    val spoken_languages_: List<SpokenLanguage>,
    val status_: String,
    val tagline_: String?,
    val type_: String,
    val vote_average_: Double,
    val vote_count_: Int
) {
    fun toModel() = ResponseTvDetailData(
        adult_ = adult_,
        backdrop_path_ = backdrop_path_,
        created_by_ = created_by_,
        episode_run_time_ = episode_run_time_,
        first_air_date_ = first_air_date_,
        genres_ = genres_,
        homepage_ = homepage_,
        id_ = id_,
        in_production_ = in_production_,
        languages_ = languages_,
        last_air_date_ = last_air_date_,
        last_episode_to_air_ = last_episode_to_air_,
        name_ = name_,
        next_episode_to_air_ = next_episode_to_air_,
        networks_ = networks_,
        number_of_episodes_ = number_of_episodes_,
        number_of_seasons_ = number_of_seasons_,
        origin_country_ = origin_country_,
        original_language_ = original_language_,
        original_name_ = original_name_,
        overview_ = overview_,
        popularity_ = popularity_,
        poster_path_ = poster_path_,
        production_companies_ = production_companies_,
        production_countries_ = production_countries_,
        seasons_ = seasons_,
        spoken_languages_ = spoken_languages_,
        status_ = status_,
        tagline_ = tagline_,
        type_ = type_,
        vote_average_ = vote_average_,
        vote_count_ = vote_count_
    )
}
