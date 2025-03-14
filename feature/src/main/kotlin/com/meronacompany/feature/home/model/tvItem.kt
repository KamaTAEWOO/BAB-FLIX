package com.meronacompany.feature.home.model

data class tvItem(
    val id: Int,               // 아이디
    val genreIds: List<Int>,   // 장르 ID 리스트
    val title: String,         // 영화 제목
    val voteAverage: Double,   // 별점
    val posterPath: String     // 포스터 경로
)