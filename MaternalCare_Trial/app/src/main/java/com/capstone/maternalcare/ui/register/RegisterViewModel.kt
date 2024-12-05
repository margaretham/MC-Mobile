package com.capstone.maternalcare.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.maternalcare.data.api.main.ApiConfig
import com.capstone.maternalcare.data.model.ResponseData
import com.capstone.maternalcare.data.model.User
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.util.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun checkUsername(username: String, callback: ResponseCallback) {
        _isLoading.value = true
        ApiConfig.getApiService().checkUser(username).enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    if (responseBody.status == "Success") {
                        callback.getCallback("User Found!", true)
                        _isLoading.value = false
                    } else {
                        callback.getCallback("Registration Failed", false)
                        _isLoading.value = false
                    }
                } else {
                    callback.getCallback(response.message(), false)
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                callback.getCallback(t.message.toString(), false)
                _isLoading.value = false
            }
        })
    }

    fun checkEmail(email: String, callback: ResponseCallback) {
        _isLoading.value = true
        ApiConfig.getApiService().checkEmail(email).enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    if (responseBody.status == "Success") {
                        callback.getCallback("User Found!", true)
                        _isLoading.value = false
                    } else {
                        callback.getCallback("Registration Failed", false)
                        _isLoading.value = false
                    }
                } else {
                    callback.getCallback(response.message(), false)
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                callback.getCallback(t.message.toString(), false)
                _isLoading.value = false
            }
        })
    }
}
