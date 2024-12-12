package com.capstone.maternalcare.ui.recomendations


import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.capstone.maternalcare.databinding.ActivityDetailArticleBinding


class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }


    private fun setupView() {
        supportActionBar?.hide()
        val data = intent.getStringExtra("urlArticle")
        val webViewClient = WebViewClient()


        binding.apply {
            webView.webViewClient = webViewClient
            webView.loadUrl(data ?: "")
        }
    }


    inner class WebViewClient : android.webkit.WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }


        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.progressBar.visibility = View.GONE
        }
    }
}