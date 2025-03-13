package com.meronacompany.feature.detail

data class DetailModel(
    // 타이틀
    val title: String,
    // 감독
    val director: String,
    // 출연
    val cast: String,
    // 재생시간
    val duration: String,
    // 장르
    val genre: String,
    // 개봉일
    val releaseDate: String,
    // 시청등급
    val rating: String,
    // 원어
    val originalLanguage: String,
    // 별점
    val ratingScore: String,
    // 줄거리
    val overview: String,
)