package com.capstone.maternalcare.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.maternalcare.data.model.UserHome
import com.capstone.maternalcare.data.model.UserPreference


class HomeViewModel(private val pref: UserPreference) : ViewModel() {


    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> get() = _progress


    private var user: UserHome = UserHome("Amelia", "Low Risk")


    fun getUserData(): UserHome {
        return user
    }


    fun updateProgress() {
        var progressValue = 0
        _progress.value = progressValue
    }
}