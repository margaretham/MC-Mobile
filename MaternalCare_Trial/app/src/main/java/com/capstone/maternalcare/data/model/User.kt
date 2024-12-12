package com.capstone.maternalcare.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val full_name: String,
    val username: String,
    val email: String,
    val isLogin: Boolean,
    val condition: String? = null,
    val age: Int? = null
) : Parcelable

@Parcelize
data class UserRegister(
    var full_name: String,
    var username: String,
    var email: String,
    var password: String,
    var confirm_password: String,
    var telephone: String,
    var date_of_birth: String,
) : Parcelable

@Parcelize
data class UserLogin(
    var full_name: String,
    var username: String,
    var email: String,
    var password: String,
    var telephone: String,
    var date_of_birth: String,
    val created_at: String,
    val updated_at: String,
    val id: Int,
) : Parcelable

@Parcelize
data class UserUpdate(
    var full_name: String,
    var username: String,
    var email: String,
    var telephone: String,
    var date_of_birth: String,
    val age: Int,
    val upperBloodPressure: Int,
    val lowerBloodPressure: Int,
    val bloodSugarLevel: Float,
    val bodyTemperature: Float,
    val heartRate: Int,
    val predictedRisk: String,
    val isLogin: Boolean = false
) : Parcelable

@Parcelize
data class UserHome(
    val name: String,
    val conditions: String,
) : Parcelable
