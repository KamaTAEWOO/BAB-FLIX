package com.meronacompany.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import timber.log.Timber
import java.io.StringReader

class TranslateInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val body = response.body?.string()

        if (body.isNullOrEmpty()) {
            Timber.e("Response body is null or empty")
            return response.newBuilder()
                .body("{}".toResponseBody("application/json".toMediaTypeOrNull()))
                .build()
        }

        val jsonObjectString = try {
            // JSON 파싱을 위한 lenient 모드 활성화
            val reader = JsonReader(StringReader(body)).apply { isLenient = true }
            val jsonElement = JsonParser.parseReader(reader)

            when {
                jsonElement is JsonObject -> jsonElement.toString() // JSON 객체이면 그대로 유지
                jsonElement is JsonArray && jsonElement.size() > 0 -> jsonElement[0].toString() // JSON 배열이면 첫 번째 객체 반환
                else -> {
                    Timber.e("Unexpected JSON format: $body")
                    "{}"
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to parse JSON")
            "{}"
        }

        Timber.d("Converted Retrofit Response to JSON Object: $jsonObjectString")

        // 변환된 JSON 객체를 다시 응답으로 반환
        return response.newBuilder()
            .body(jsonObjectString.toResponseBody("application/json".toMediaTypeOrNull()))
            .build()
    }
}