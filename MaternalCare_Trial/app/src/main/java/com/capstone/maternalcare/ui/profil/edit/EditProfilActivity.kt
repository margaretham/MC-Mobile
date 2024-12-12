package com.capstone.maternalcare.ui.profil.edit

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.capstone.maternalcare.data.model.PredictRequest
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.databinding.ActivityEditProfilBinding
import com.capstone.maternalcare.util.ViewModelFactory

class EditProfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfilBinding
    private lateinit var viewModel: EditProfilViewModel

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user_preferences"
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(this.dataStore))
        )[EditProfilViewModel::class.java]

        viewModel.getUser().observe(this) { user ->
            // Use null-coalescing to handle null values or provide a fallback
            binding.usernameEditText.setText(user.username)
            binding.emailEditText.setText(user.email)
            binding.fullnameEditText.setText(user.full_name)
            binding.ageEditText.setText(user.age.toString())
            binding.upperBloodPressureEditText.setText(user.upperBloodPressure.toString())
            binding.lowerBloodPressureEditText.setText(user.lowerBloodPressure.toString())
            binding.bloodSugarLevelEditText.setText(user.bloodSugarLevel.toString())
            binding.bodyTemperatureEditText.setText(user.bodyTemperature.toString())
            binding.heartRateEditText.setText(user.heartRate.toString())
        }


        viewModel.updateStatus.observe(this) { isUpdated ->
            if (isUpdated) {
                Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show()
                // You can perform further actions such as navigating back
            } else {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setupAction() {
        binding.saveButton.setOnClickListener {
            val userData = collectUserData()
            if (userData != null) {
                viewModel.updateUserProfile(userData)

                finish()
            }
        }
    }

    private fun collectUserData(): PredictRequest? {
        if (TextUtils.isEmpty(binding.fullnameEditText.text)) {
            binding.nameInputLayout.error = "Please enter your name"
            return null
        }

        return PredictRequest(
            email = binding.emailEditText.text.toString(),
            umurTahun = binding.ageEditText.text.toString().toInt(),
            tekananDiastolikMmHg = binding.upperBloodPressureEditText.text.toString().toInt(),
            tekananSistolikMmHg = binding.lowerBloodPressureEditText.text.toString().toInt(),
            gulaDarah = binding.bloodSugarLevelEditText.text.toString().toFloat(),
            temperaturTubuhF = binding.bodyTemperatureEditText.text.toString().toFloat(),
            detakJantung = binding.heartRateEditText.text.toString().toInt()
        )
    }
}
