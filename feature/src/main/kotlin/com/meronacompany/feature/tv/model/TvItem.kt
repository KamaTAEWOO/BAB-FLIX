package com.meronacompany.feature.tv.model

data class TvItem(
    val id: Int? = 0,                        // 아이디
    val genreIds: List<Int>? = emptyList(),      // 장르 ID 리스트
    val title: String? = "" ,                   // TV 제목
    val voteAverage: Double? = 0.0,             // 별점
    val posterPath: String? = ""                // 포스터 경로
)