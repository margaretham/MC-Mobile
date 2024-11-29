package com.dicoding.maternal.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.maternal.R
import com.dicoding.maternal.databinding.ActivitySplashscreenBinding
import com.dicoding.maternal.ui.login.LoginActivity
import com.dicoding.maternal.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding
    private val splashTimeOut: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

            if (isLoggedIn) {
                // Jika sudah login, langsung ke MainActivity
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                // Jika belum login, arahkan ke LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, splashTimeOut)
    }
}