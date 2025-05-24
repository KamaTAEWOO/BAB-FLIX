package com.meronacompany.core.local

import android.content.Context

object PreferenceManager {
    private const val PREF_NAME = "app_pref"
    private const val KEY_API_CALL_COUNT = "api_call_count"
    private const val KEY_LAST_RESET_DATE = "last_reset_date"

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
}