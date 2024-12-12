package com.capstone.maternalcare.data.model

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[NAME_KEY] ?: "",
                preferences[USERNAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[LOGIN_STATUS] ?: false,
                preferences[CONDITION_KEY] ?: "Low Risk",
                preferences[AGE_KEY] ?: 0
            )
        }
    }

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = user.full_name
            preferences[USERNAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
            preferences[LOGIN_STATUS] = user.isLogin
        }
    }

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    suspend fun login(user: User) {
        dataStore.edit { preferences ->
            preferences[LOGIN_STATUS] = true
            preferences[NAME_KEY] = user.full_name
            preferences[USERNAME_KEY] = user.username
            preferences[EMAIL_KEY] = user.email
        }
    }

    fun getBoardingPage(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[BOARDING_KEY] ?: true
        }
    }

    suspend fun saveBoardingPage(setData : Boolean ){
        dataStore.edit { preferences ->
            preferences[BOARDING_KEY] = setData

        }
    }

    suspend fun saveUserProfile(userData: PredictRequest, predict: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = userData.email.toString()
            preferences[AGE_KEY] = userData.umurTahun.toString().toInt()
            preferences[UPPER_BP_KEY] = userData.tekananDiastolikMmHg.toString().toInt()
            preferences[LOWER_BP_KEY] = userData.tekananSistolikMmHg.toString().toInt()
            preferences[BLOOD_SUGAR_KEY] = userData.gulaDarah.toString().toFloat()
            preferences[BODY_TEMP_KEY] = userData.temperaturTubuhF.toString().toFloat()
            preferences[HEART_RATE_KEY] = userData.detakJantung.toString().toInt()
            preferences[PREDICTED_RISK_KEY] = predict
        }
    }


    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(NAME_KEY)
            preferences.remove(USERNAME_KEY)
            preferences.remove(EMAIL_KEY)
            preferences.remove(PASSWORD_KEY)
            preferences.remove(TEL_KEY)
            preferences.remove(BIRTH_KEY)
            preferences.remove(CREATED_AT)
            preferences.remove(UPDATED_AT)
            preferences.remove(ID_KEY)
            preferences.remove(LOGIN_STATUS)
        }
    }

    // New method to retrieve user update data
    fun getUserUpdate(): Flow<UserUpdate> {
        return dataStore.data.map { preferences ->
            val userUpdate = UserUpdate(
                full_name = preferences[NAME_KEY] ?: "",
                username = preferences[USERNAME_KEY] ?: "",
                email = preferences[EMAIL_KEY] ?: "",
                telephone = preferences[TEL_KEY] ?: "",
                date_of_birth = preferences[BIRTH_KEY] ?: "",
                age = preferences[AGE_KEY] ?: 0,
                upperBloodPressure = preferences[UPPER_BP_KEY] ?: 0,
                lowerBloodPressure = preferences[LOWER_BP_KEY] ?: 0,
                bloodSugarLevel = preferences[BLOOD_SUGAR_KEY] ?: 0f,
                bodyTemperature = preferences[BODY_TEMP_KEY] ?: 0f,
                heartRate = preferences[HEART_RATE_KEY] ?: 0,
                predictedRisk = preferences[PREDICTED_RISK_KEY] ?: "Low Risk",
                isLogin = preferences[LOGIN_STATUS] ?: false
            )

            // Log userUpdate to verify values
            Log.d("UserUpdate", "Retrieved UserUpdate: $userUpdate")
            userUpdate
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val TEL_KEY = stringPreferencesKey("telephone")
        private val CREATED_AT = stringPreferencesKey("ca")
        private val UPDATED_AT = stringPreferencesKey("ua")
        private val ID_KEY = intPreferencesKey("id")
        private val BIRTH_KEY = stringPreferencesKey("birth")
        private val LOGIN_STATUS =
            booleanPreferencesKey("login_status")
        private val EMAIL_KEY = stringPreferencesKey("email")

        private val THEME_KEY = booleanPreferencesKey("theme_setting")
        private val BOARDING_KEY = booleanPreferencesKey("boarding_page")

        // Define the new keys for health-related fields
        private val AGE_KEY = intPreferencesKey("age")
        private val UPPER_BP_KEY = intPreferencesKey("upper_blood_pressure")
        private val LOWER_BP_KEY = intPreferencesKey("lower_blood_pressure")
        private val BLOOD_SUGAR_KEY = floatPreferencesKey("blood_sugar")
        private val BODY_TEMP_KEY = floatPreferencesKey("body_temperature")
        private val HEART_RATE_KEY = intPreferencesKey("heart_rate")
        private val PREDICTED_RISK_KEY = stringPreferencesKey("predicted_risk")

        private val CONDITION_KEY = stringPreferencesKey("condition")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}