package com.meronacompany.domain.model

data class ResponseMovieVideo(
    val id: Int,
    val results: List<VideoResult>
)

data class VideoResult(
    val iso_639_1: String,
    val iso_3166_1: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    val published_at: String,
    val id: String
)