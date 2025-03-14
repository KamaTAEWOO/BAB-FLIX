package com.meronacompany.feature.detail

data class DetailTvModel(
    // 타이틀
    val title: String,
    // 창작자
    val creator: String,
    // 출연
    val cast: String,
    // 장르
    val genre: String,
    // 원어
    val originalLanguage: String,
    // 별점
    val ratingScore: String,
    // 줄거리
    val overview: String,
)