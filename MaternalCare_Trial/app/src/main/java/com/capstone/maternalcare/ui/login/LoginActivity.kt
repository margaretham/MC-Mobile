package com.capstone.maternalcare.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.maternalcare.MainActivity
import com.capstone.maternalcare.R
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.databinding.ActivityLoginBinding
import com.capstone.maternalcare.ui.register.RegisterActivity
import com.capstone.maternalcare.util.ResponseCallback
import com.capstone.maternalcare.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    private val validUsername = "user123"
    private val validPassword = "password123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()// Jangan lupa finish() agar tidak bisa kembali ke login activity
            }
        }
    }

    private fun playAnimation() {
        val logoImageView = ObjectAnimator.ofFloat(binding.logoImageView, View.ALPHA, 1f).setDuration(300)
        val appNameTextView = ObjectAnimator.ofFloat(binding.appNameTextView, View.ALPHA, 1f).setDuration(300)
        val emailEditText = ObjectAnimator.ofFloat(binding.usernameEditText, View.ALPHA, 1f).setDuration(300)
        val passwordEditText = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(300)
        val loginButton = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(300)
        val dontHaveAccountTextView = ObjectAnimator.ofFloat(binding.dontHaveAccountTextView, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(logoImageView, appNameTextView, emailEditText, passwordEditText, loginButton, dontHaveAccountTextView)
            start()
        }
    }

    private fun setupAction() {

        binding.apply {

            loginButton.setOnClickListener {
                var isError = false

                // Validate email
                if (TextUtils.isEmpty(usernameEditText.text)) {
                    isError = true
                    usernameEditText.error = getString(R.string.enter_username)
                }

                // Validate password
                if (TextUtils.isEmpty(passwordEditText.text)) {
                    isError = true
                    passwordEditText.error = getString(R.string.password)
                } else if (passwordEditText.text?.length!! < 6) {
                    isError = true
                    passwordEditText.error = getString(R.string.password)
                }

                // If no error, proceed to login
                if(!isError){
                    val isConnectedToApi = checkApiConnection()

                    if (isConnectedToApi) {
                        viewModel.loginUser(username = usernameEditText.text.toString(), password = passwordEditText.text.toString(), object : ResponseCallback {
                            override fun getCallback(msg: String, status: Boolean) {
                                if (!status) {
                                    showDialogs(msg, status)
//                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                                    startActivity(intent)
//                                    finish()  // Menutup L
                                } else {
                                    showDialogs(msg, status)
//                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                                    startActivity(intent)
//                                    finish()  // Menutup L
                                }
                            }
                        })
                    } else {
                        if (usernameEditText.text.toString() == validUsername && passwordEditText.text.toString() == validPassword) {
                            showDialogs("Login Successful", true)
                        } else {
                            showDialogs("Invalid username or password", false)
                        }
                    }
                }
            }


            dontHaveAccountTextView.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    private fun checkApiConnection(): Boolean {
        return true
    }

    private fun showDialogs(msg: String, status: Boolean) {
        if (status) {
            AlertDialog.Builder(this).apply {
                setTitle("Yay !")
                setMessage(msg)
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle("Oops")
                setMessage(msg)
                setNegativeButton(getString(R.string.repeat)) { dialog, _ -> dialog.dismiss() }
                create()
                show()
            }
        }
    }
}
