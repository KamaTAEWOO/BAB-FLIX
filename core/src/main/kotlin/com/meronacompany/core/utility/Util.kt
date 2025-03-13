package com.meronacompany.core.utility

import android.annotation.SuppressLint

object Util {

    @SuppressLint("DefaultLocale")
    fun formatVoteAverage(voteAverage: Double): String {
        return String.format("%.1f", voteAverage)
    }

    fun formatDuration(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return if (hours > 0) {
            "$hours 시간 $remainingMinutes 분"
        } else {
            "$remainingMinutes 분"
        }
    }
}