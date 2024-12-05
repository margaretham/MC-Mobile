package com.capstone.maternalcare.data.api.articles

import com.capstone.maternalcare.data.model.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceArticles {
    @GET("v2/everything")
    fun getNews(
        @Query("q") searchQuery: String,
        @Query("sortBy") sortBy: String? = "",
        @Query("apiKey") apiKey: String,
        @Query("language") language: String? = "en",
    ): Call<ArticlesResponse>
}