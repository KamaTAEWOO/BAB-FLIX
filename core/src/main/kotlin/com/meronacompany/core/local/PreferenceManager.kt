package com.meronacompany.core.local

import android.content.Context

object PreferenceManager {
    private const val PREF_NAME = "app_pref"
    private const val KEY_API_CALL_COUNT = "api_call_count"

    fun getApiCallCount(context: Context): Int {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_API_CALL_COUNT, 0)
    }

    fun incrementAPICallCount(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val current = prefs.getInt(KEY_API_CALL_COUNT, 0)
        prefs.edit().putInt(KEY_API_CALL_COUNT, current + 1).apply()
    }

    fun clearApiCallCount(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_API_CALL_COUNT).apply()
    }
}