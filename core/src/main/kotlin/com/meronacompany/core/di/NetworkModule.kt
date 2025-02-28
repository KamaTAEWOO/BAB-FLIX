package com.meronacompany.core.di

import com.meronacompany.core.BuildConfig.TMDB_API_KEY
import com.meronacompany.core.network.service.HomeService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    // Retrofit 생성
    fun provideRetrofit(): Retrofit {
        // HttpLoggingInterceptor 설정
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // 요청/응답 본문을 로그로 출력
        }

        // 고정 헤더를 추가하는 Interceptor 설정
        val headerInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val requestWithHeaders = originalRequest.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $TMDB_API_KEY")
                // 필요한 다른 헤더들도 추가 가능
                .build()
            chain.proceed(requestWithHeaders)
        }

        // OkHttpClient에 interceptor 추가
        val client = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        // Retrofit 객체 생성
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)  // OkHttpClient를 Retrofit에 추가
            .addConverterFactory(GsonConverterFactory.create())  // Gson converter 추가
            .build()
    }
}