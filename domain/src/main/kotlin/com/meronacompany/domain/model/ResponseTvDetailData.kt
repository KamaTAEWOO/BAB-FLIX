package com.meronacompany.domain.model

data class ResponseTvDetailData(
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
)

data class Creator(
    val id_: Int,
    val credit_id_: String,
    val name_: String,
    val original_name_: String,
    val gender_: Int,
    val profile_path_: String?
)

data class Episode(
    val id_: Int,
    val name_: String,
    val overview_: String,
    val vote_average_: Double,
    val vote_count_: Int,
    val air_date_: String?,
    val episode_number_: Int,
    val episode_type_: String?,
    val production_code_: String?,
    val runtime_: Int?,
    val season_number_: Int,
    val show_id_: Int,
    val still_path_: String?
)

data class Network(
    val id_: Int,
    val logo_path_: String?,
    val name_: String,
    val origin_country_: String
)

data class ProductionCountry(
    val iso_3166_1_: String,
    val name_: String
)

data class Season(
    val air_date_: String?,
    val episode_count_: Int,
    val id_: Int,
    val name_: String,
    val overview_: String,
    val poster_path_: String?,
    val season_number_: Int,
    val vote_average_: Double
)
