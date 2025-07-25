package com.meronacompany.core.local

import android.content.Context
import androidx.core.content.edit
import timber.log.Timber

object PreferenceManager {
    private const val PREF_NAME = "app_pref"
    private const val KEY_API_CALL_COUNT = "api_call_count"
    private const val KEY_LAST_RESET_DATE = "last_reset_date"
    private const val KEY_LANGUAGE = "language"

    fun getApiCallCount(context: Context): Int {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_API_CALL_COUNT, 0)
    }

    fun incrementAPICallCount(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val current = prefs.getInt(KEY_API_CALL_COUNT, 0)
        prefs.edit().putInt(KEY_API_CALL_COUNT, current + 1).apply()
    }

    // 날짜가 변경되면 카운트를 초기화하는 메서드
    fun resetApiCallCountIfDateChanged(context: Context, lastResetDate: Long) {
        val currentDate = System.currentTimeMillis()
        if (currentDate > lastResetDate) {
            clearApiCallCount(context)
        }
    }

    fun getLastResetDate(context: Context): Long {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getLong(KEY_LAST_RESET_DATE, 0L)
    }

    fun updateLastResetDate(context: Context, timestamp: Long) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putLong(KEY_LAST_RESET_DATE, timestamp)
            .apply()
    }

    fun getTodayMidnightTimestamp(): Long {
        val now = System.currentTimeMillis()
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = now
            set(java.util.Calendar.HOUR_OF_DAY, 0)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    // 2분마다 카운트를 초기화하는 메서드
//    fun checkAndResetApiCallCountIfNeeded(context: Context) {
//        val lastResetDate = getLastResetDate(context)
//        val now = System.currentTimeMillis()
//        val twoMinutesAgo = now - 2 * 60 * 1000 // 2 minutes in milliseconds
//        if (lastResetDate < twoMinutesAgo) {
//            clearApiCallCount(context)
//            updateLastResetDate(context, now)
//        }
//    }

    // 하루 카운트
    fun checkAndResetApiCallCountIfNeeded(context: Context) {
        val lastResetDate = getLastResetDate(context)
        val todayMidnight = getTodayMidnightTimestamp()
        if (todayMidnight > lastResetDate) {
            clearApiCallCount(context)
            updateLastResetDate(context, todayMidnight)
        }
    }

    // API 호출 카운트를 초기화하는 메서드
    fun clearApiCallCount(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_API_CALL_COUNT).apply()
    }

    // 언어 설정을 저장하는 메서드
    fun setLanguage(context: Context, language: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit {
                putString(KEY_LANGUAGE, language)
            }
    }

    // 언어 설정을 가져오는 메서드
    fun getLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val savedLanguage = prefs.getString(KEY_LANGUAGE, "")

        return if (savedLanguage.isNullOrEmpty()) {
            val deviceLanguage = when (val locale = context.resources.configuration.locales[0]) {
                null -> "ko" // fallback
                else -> locale.toLanguageTag()
            }

            prefs.edit { putString(KEY_LANGUAGE, deviceLanguage) }
            deviceLanguage
        } else {
            savedLanguage
        }
    }
}