package com.meronacompany.core.utility

import android.annotation.SuppressLint

object Util {

    @SuppressLint("DefaultLocale")
    fun formatVoteAverage(voteAverage: Double): String {
        return String.format("%.1f", voteAverage)
    }
}