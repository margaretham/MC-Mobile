package com.capstone.maternalcare.data.api.predict

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfigUser {
    companion object {
        fun getApiService(): ApiServiceUser {
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
                .baseUrl("https://maternalcare-937922139514.asia-southeast2.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiServiceUser::class.java)
        }
    }
}
