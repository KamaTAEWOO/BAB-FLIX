package com.meronacompany.core.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.meronacompany.core.BuildConfig
import com.meronacompany.core.network.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    // Retrofit 생성
    fun provideRetrofit(context: Context): Retrofit {
        // HttpLoggingInterceptor 설정
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // 요청/응답 본문을 로그로 출력
        }

        // OkHttpClient에 interceptor 추가
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(HeaderInterceptor())
            .build()

        val gson = GsonBuilder().setLenient().create()

        // Retrofit 객체 생성 (Custom JsonObjectConverterFactory 사용)
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)  // OkHttpClient를 Retrofit에 추가
            .addConverterFactory(GsonConverterFactory.create(gson))  // Gson converter 추가
            .build()
    }
}