package com.capstone.maternalcare.ui.register

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.maternalcare.MainActivity
import com.capstone.maternalcare.R
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.data.model.UserRegister
import com.capstone.maternalcare.databinding.ActivityRegister2Binding
import com.capstone.maternalcare.ui.login.LoginActivity
import com.capstone.maternalcare.util.ResponseCallback
import com.capstone.maternalcare.util.ViewModelFactory
import com.capstone.maternalcare.util.getAges

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class RegisterActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityRegister2Binding
    private lateinit var viewModel: RegisterViewModel2
    private lateinit var userRegister: UserRegister

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        userRegister = intent.getParcelableExtra<UserRegister>("userRegister") as UserRegister

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[RegisterViewModel2::class.java]

        viewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                val intent = Intent(this@RegisterActivity2, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAction() {
        showLoading()
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnPrevious.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val tel = binding.phoneEditText.text.toString()
            val dateOfBirth = binding.datePicker.year.toString() + "-" + (binding.datePicker.month + 1).toString() + "-" + binding.datePicker.dayOfMonth.toString()
            var isError = false

            binding.apply {
                if (getAges(dateOfBirth) < 0) {
                    isError = true
                    datePicker.setBackgroundResource(R.drawable.bg_edit_text_error)
                    tvError.visibility = View.VISIBLE
                    tvError.text = getString(R.string.wrong_date_of_birth)
                }

                if (tel.isEmpty()) {
                    isError = true
                    phoneEditText.error = getString(R.string.enter_tel)
                }

                if (!isError) {
                    userRegister.telephone = tel
                    userRegister.date_of_birth = dateOfBirth

                    // Simulasi sukses dan lanjut ke MainActivity atau menggunakan API
                    val isConnectedToApi = checkApiConnection()

                    if (isConnectedToApi) {
                        // Gunakan API untuk registrasi
                        viewModel.saveUser(userRegister, object : ResponseCallback {
                            override fun getCallback(msg: String, status: Boolean) {
                                showDialogs(msg, status)
                                val intent = Intent(this@RegisterActivity2, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        })
                    } else {
                        // Simulasi sukses registrasi lokal, tetap lanjut ke MainActivity
                        showDialogs(getString(R.string.registration_success), false)

                        // Arahkan ke MainActivity meskipun tidak ada API
                        val intent = Intent(this@RegisterActivity2, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun checkApiConnection(): Boolean {
        return true // Simulasi API selalu berhasil, ganti dengan logika yang sesuai jika perlu
    }

    private fun showDialogs(msg: String, status: Boolean) {
        if (status) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.success))
                setMessage(getString(R.string.registration_success))
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    val intent = Intent(this@RegisterActivity2, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.sorry))
                val message = getString(R.string.create_account_failed, msg)
                setMessage(message)
                setPositiveButton(getString(R.string.repeat)) { _, _ -> finish() }
                create()
                show()
            }
        }
    }

    private fun showLoading() {
        viewModel.isLoading.observe(this) {
            binding.apply {
                when {
                    it -> progressBar.visibility = View.VISIBLE
                    else -> progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}
