package com.capstone.maternalcare.ui.profil

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.databinding.FragmentProfileBinding
import com.capstone.maternalcare.ui.login.LoginViewModel
import com.capstone.maternalcare.ui.profil.edit.EditProfilActivity
import com.capstone.maternalcare.ui.profil.help.HelpActivity
import com.capstone.maternalcare.ui.profil.settings.SettingsActivity
import com.capstone.maternalcare.util.ViewModelFactory

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val Context.dataStore by preferencesDataStore(name = "user_preferences")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()

        setupViewModel()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewModel() {
        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
        )[ProfilViewModel::class.java]

        viewModel.getUser().observe(viewLifecycleOwner) { user ->
            binding.usernameEditText.text = "Username: ${user.username}"
            binding.fullnameEditText.text = "Fullname: ${user.username}"
            binding.ageEditText.text = "Age: ${user.age} years old"
            binding.upperBloodPressureTextView.text = "Upper Blood Pressure: ${user.upperBloodPressure}"
            binding.lowerBloodPressureTextView.text = "Lower Blood Pressure: ${user.lowerBloodPressure}"
            binding.bloodSugarLevelTextView.text = "Blood Sugar Level: ${user.bloodSugarLevel}"
            binding.bodyTemperatureTextView.text = "Body Temperature: ${user.bodyTemperature}"
            binding.heartRateTextView.text = "Heart Rate: ${user.heartRate}"
            binding.kondisiTextView.text = user.predictedRisk
        }
    }

    private fun setupAction() {
        binding.apply {
            editButton.setOnClickListener {
                val intent = Intent(requireContext(), EditProfilActivity::class.java)
                startActivity(intent)
            }
        }
        binding.apply {
            settingsButton.setOnClickListener {
                val intent = Intent(requireContext(), SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        binding.apply {
            helpButton.setOnClickListener {
                val intent = Intent(requireContext(), HelpActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
