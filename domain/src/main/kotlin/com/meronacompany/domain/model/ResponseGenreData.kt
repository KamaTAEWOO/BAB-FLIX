package com.meronacompany.domain.model

data class ResponseGenreData (
    val genres: List<GenreData>
)

data class GenreData(
    val id: Int,
    val name: String
)