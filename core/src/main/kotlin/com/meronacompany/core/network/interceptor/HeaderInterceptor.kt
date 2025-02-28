package com.meronacompany.core.network.interceptor

import com.meronacompany.core.BuildConfig.TMDB_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer $TMDB_API_KEY")
            .build()

        return chain.proceed(newRequest)
    }
}