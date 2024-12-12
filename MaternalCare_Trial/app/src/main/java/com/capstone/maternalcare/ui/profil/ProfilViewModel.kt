package com.capstone.maternalcare.ui.profil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.maternalcare.data.model.User
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.data.model.UserUpdate
import kotlinx.coroutines.launch

class ProfilViewModel (private val pref: UserPreference) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserUpdate> {
        return pref.getUserUpdate().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}