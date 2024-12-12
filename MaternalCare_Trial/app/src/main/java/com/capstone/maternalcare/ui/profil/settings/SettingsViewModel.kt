package com.capstone.maternalcare.ui.profil.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.maternalcare.data.model.User
import com.capstone.maternalcare.data.model.UserPreference
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: UserPreference) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            pref.logout()
            onSuccess()
        }
    }
}