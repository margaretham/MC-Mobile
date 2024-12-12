package com.capstone.maternalcare.data.api.main

import com.capstone.maternalcare.data.api.predict.ApiServiceUser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            // Create a logging interceptor instance
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Log the full request/response body
            }

            // Build the OkHttpClient with the logging interceptor
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor) // Add the interceptor to the client
                .addInterceptor {
                    val original = it.request()
                    val requestBuilder = original.newBuilder()
                    val request = requestBuilder.build()
                    it.proceed(request)
                }
                .build()

            // Build the Retrofit instance with the OkHttpClient
            val retrofit = Retrofit.Builder()
                .baseUrl("https://maternalcare-bangkit.et.r.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
