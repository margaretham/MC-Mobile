package com.capstone.maternalcare.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.maternalcare.data.model.UserHome  // Import the correct model
import com.capstone.maternalcare.data.model.UserPreference

class HomeViewModel(pref: UserPreference) : ViewModel() {

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> get() = _progress

    private var user: UserHome = UserHome("Narendra Wicaksono", "2 years")  // Example UserHome data

    fun getUserData(): UserHome {
        return user
    }

    fun updateProgress() {
        var progressValue = 0
        while (progressValue <= 100) {
            _progress.value = progressValue
            progressValue += 10
        }
    }
}
