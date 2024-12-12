package com.capstone.maternalcare.ui.profil.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.maternalcare.data.api.predict.ApiConfigUser
import com.capstone.maternalcare.data.model.PredictRequest
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.data.model.UserUpdate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class EditProfilViewModel(private val pref: UserPreference) : ViewModel() {

    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> get() = _updateStatus

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getUser(): LiveData<UserUpdate> {
        return pref.getUserUpdate().asLiveData()
    }

    fun updateUserProfile(userData: PredictRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiConfigUser.getApiService().sendData(userData).execute()

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _updateStatus.postValue(true)
                        saveRisk(userData, body.predictedRisk)
                    } else {
                        _updateStatus.postValue(false)
                        _errorMessage.postValue("Response Error: Empty Response Body")
                    }
                } else {
                    _updateStatus.postValue(false)
                    _errorMessage.postValue("Response Error: ${response.message()}")
                }
            } catch (e: HttpException) {
                _updateStatus.postValue(false)
                _errorMessage.postValue("HTTP Exception: ${e.message()}")
            } catch (e: Exception) {
                _updateStatus.postValue(false)
                _errorMessage.postValue("Unexpected Error: ${e.localizedMessage}")
            }
        }
    }

    private fun saveRisk(userData: PredictRequest, status: String?) {
        viewModelScope.launch(Dispatchers.Main) {
            val risk = status ?: "Low Risk"
            pref.saveUserProfile(userData, risk)
        }
    }

}
