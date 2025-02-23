package com.meronacompany.core.network.service

import com.meronacompany.core.model.dto.CommonDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeService {

    @GET("authentication")
    suspend fun requestIsApiKey(
        @Header("Accept") accept: String = "application/json",
        @Header("Authorization") authorization: String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NTNkMTg2MzFjNGMxZGExZmEyNjA5MTNhMjIzNTczMyIsIm5iZiI6MTczOTcxMzMxNC44MzUsInN1YiI6IjY3YjFlYjIyNzI5NGJlOThhOGUwZGViNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3jzsvFYt-H8gkv3CAF3qBIjwf3sUrq8V5DNqTGfe04o"
    ): CommonDto

}