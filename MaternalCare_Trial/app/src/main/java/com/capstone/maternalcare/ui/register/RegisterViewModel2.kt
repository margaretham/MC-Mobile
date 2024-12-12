package com.capstone.maternalcare.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.maternalcare.data.api.main.ApiConfig
import com.capstone.maternalcare.data.model.RegisterRequest
import com.capstone.maternalcare.data.model.RegisterResponse
import com.capstone.maternalcare.data.model.User
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.data.model.UserRegister
import com.capstone.maternalcare.util.ResponseCallback
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel2(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(userRegister: UserRegister, callback: ResponseCallback) {
        _isLoading.value = true
        ApiConfig.getApiService().register(
            RegisterRequest(
                username = userRegister.username,
                fullName = userRegister.full_name,
                email = userRegister.email,
                password = userRegister.password,
                confirmPassword = userRegister.password,
                phoneNumber = userRegister.telephone,
                dateOfBirth = userRegister.date_of_birth
            )
        ).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    if (responseBody.user != null) {
                        // Successful registration, save user data
                        callback.getCallback("Registration Success", true)
                        _isLoading.value = false
                        saveUserData(User(
                            full_name = userRegister.full_name,
                            username = userRegister.username,
                            email = userRegister.email,
                            isLogin = true
                        ))
                        Log.d("AddUser", "onResponse: Registration success: ${responseBody.user}")
                    } else {
                        // Response body is successful but user data is null
                        callback.getCallback("Registration Failed: User data is null", false)
                        _isLoading.value = false
                        Log.e("AddUser", "onResponse: User data is null, registration failed.")
                    }
                } else {
                    // Unsuccessful response, log the error and show the message
                    callback.getCallback("Registration Failed: ${response.message()}", false)
                    _isLoading.value = false
                    Log.e("AddUser", "onResponse: Failed response, message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                // Log network error and pass error message to callback
                callback.getCallback("Error: ${t.message}", false)
                _isLoading.value = false
                Log.e("AddUser", "onFailure: Error during registration: ${t.message}", t)
            }
        })
    }

    fun saveUserData(user: User) {
        viewModelScope.launch {
            try {
                pref.saveUser(user)  // Save user data to preferences
                Log.d("AddUser", "saveUserData: User saved successfully")
            } catch (e: Exception) {
                Log.e("AddUser", "saveUserData: Error saving user data: ${e.localizedMessage}", e)
            }
        }
    }

}