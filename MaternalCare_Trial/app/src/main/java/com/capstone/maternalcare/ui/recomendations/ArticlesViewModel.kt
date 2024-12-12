package com.capstone.maternalcare.ui.recomendations


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.maternalcare.data.api.articles.ApiConfigArticles
import com.capstone.maternalcare.data.model.ArticlesResponse
import com.capstone.maternalcare.util.ARTICLE_API_KEY
import com.capstone.maternalcare.util.DEFAULT_SORT_BY_ARTICLES
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ArticlesViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    private val listArticles = MutableLiveData<ArticlesResponse>()


    fun setNews(query: String) {
        _isLoading.value = true
        ApiConfigArticles.getApiService().getNews(
            query,
            DEFAULT_SORT_BY_ARTICLES
        ).enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("TAG", "onResponse: ${response.message()}")
                    listArticles.postValue(response.body())
                    _isLoading.value = false
                } else {
                    Log.d("TAG", "onResponse error: ${response.message()}")
                    _isLoading.value = false
                }
            }


            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.d("Failure", "onFailure: ${t.message}")
                _isLoading.value = false
            }
        })
    }


    fun getNews(): LiveData<ArticlesResponse> {
        return listArticles
    }
}