package com.meronacompany.core.di

import com.meronacompany.core.network.service.HomeService
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

        // OkHttpClient에 interceptor 추가
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        // Retrofit 객체 생성
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)  // OkHttpClient를 Retrofit에 추가
            .addConverterFactory(GsonConverterFactory.create())  // Gson converter 추가
            .build()
    }

    // HomeService 제공
    fun provideHomeService(retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }
}