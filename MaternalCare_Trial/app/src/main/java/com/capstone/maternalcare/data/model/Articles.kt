package com.capstone.maternalcare.data.model


data class ArticlesResponse(
    val articles: ArrayList<Article>? = null,
    val status: String? = null,
    val totalResult: Int? = null
)


data class Article(
    val description: String?,
    val source: String?,
    val title: String?,
    val url: String?
)


data class Source(
    val id: Any? = null,
    val name: String? = null
)