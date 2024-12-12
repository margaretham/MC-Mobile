package com.capstone.maternalcare.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.capstone.maternalcare.MainActivity
import com.capstone.maternalcare.R
import com.capstone.maternalcare.databinding.ActivitySplashscreenBinding
import com.capstone.maternalcare.ui.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
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
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // Jika belum login, arahkan ke LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, splashTimeOut)
    }
}