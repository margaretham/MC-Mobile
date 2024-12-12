package com.capstone.maternalcare.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.maternalcare.MainActivity
import com.capstone.maternalcare.R
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.data.model.UserRegister
import com.capstone.maternalcare.databinding.ActivityRegisterBinding
import com.capstone.maternalcare.ui.login.LoginActivity
import com.capstone.maternalcare.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var userRegister: UserRegister

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRegister = UserRegister("","","","","","", date_of_birth = "")

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        val titleTextView = ObjectAnimator.ofFloat(binding.appNameTextView, View.ALPHA, 1f).setDuration(300)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(300)
        val usernameEditTextLayout = ObjectAnimator.ofFloat(binding.usernameEditText, View.ALPHA, 1f).setDuration(300)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditText, View.ALPHA, 1f).setDuration(300)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(300)
        val registerButton = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(300)
        val alreadyHaveAccountTextView = ObjectAnimator.ofFloat(binding.alreadyHaveAccountTextView, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(titleTextView, emailEditTextLayout, usernameEditTextLayout, nameEditTextLayout, passwordEditTextLayout, registerButton, alreadyHaveAccountTextView)
            start()
        }
    }

    private fun setupAction() {
        binding.alreadyHaveAccountTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val username = binding.usernameEditText.text.toString()
            val checkSpaces = "\\A\\w{1,20}\\z"

            when {
                !username.matches(checkSpaces.toRegex()) -> {
                    binding.usernameEditText.error = getString(R.string.no_whitespace)
                }
                username.isEmpty() -> {
                    binding.usernameEditText.error = getString(R.string.enter_username)
                }
                name.isEmpty() -> {
                    binding.nameEditText.error = getString(R.string.enter_name)
                }
                email.isEmpty() -> {
                    binding.emailEditText.error = getString(R.string.enter_email)
                }
                password.isEmpty() -> {
                    binding.passwordEditText.error = getString(R.string.enter_password)
                }
                else -> {
                    // Simulasikan sukses dan lanjut ke RegisterActivity2
                    userRegister = UserRegister(
                        full_name = name,
                        username = username,
                        password = password,
                        confirm_password = password,
                        email = email,
                        telephone = "",
                        date_of_birth = ""
                    )
                    showDialogError(getString(R.string.registration_success), false)
                }
            }
        }
    }

    private fun showDialogError(msg: String, status: Boolean) {
        if (!status) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.success))
                setMessage(getString(R.string.registration_success))
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    val intent = Intent(this@RegisterActivity, RegisterActivity2::class.java)
                    intent.putExtra("userRegister", userRegister)
                    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@RegisterActivity,
                        Pair(binding.logoImageView, "logo"),
                        Pair(binding.appNameTextView, "title"),
                        Pair(binding.alreadyHaveAccountTextView, "txtLogin")
                    )
                    startActivity(intent, optionsCompat.toBundle())
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.sorry))
                setMessage(msg)
                setPositiveButton(getString(R.string.repeat)) { dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[RegisterViewModel::class.java]

        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                startActivity(
                    Intent(
                        this@RegisterActivity,
                        MainActivity::class.java
                    )
                )
            }
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}
