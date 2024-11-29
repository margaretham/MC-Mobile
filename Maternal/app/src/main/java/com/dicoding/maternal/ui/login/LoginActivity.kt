package com.dicoding.maternal.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.maternal.R
import com.dicoding.maternal.databinding.ActivityLoginBinding
import com.dicoding.maternal.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle login button click
        binding.loginButton.setOnClickListener {
            // Validasi login (contoh sederhana)
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                binding.errorTextView.text = ""
                startActivity(intent)
                finish()
            } else {
                binding.errorTextView.text = "Username dan password harus diisi"
            }
        }
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isLoggedIn", true)
            apply()
        }
    }
}