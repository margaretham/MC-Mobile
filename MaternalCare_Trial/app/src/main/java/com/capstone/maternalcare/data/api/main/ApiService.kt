package com.capstone.maternalcare.data.api.main

import com.capstone.maternalcare.data.model.LoginRequest
import com.capstone.maternalcare.data.model.LoginResponse
import com.capstone.maternalcare.data.model.RegisterRequest
import com.capstone.maternalcare.data.model.RegisterResponse
import com.capstone.maternalcare.data.model.ResponseData
import com.capstone.maternalcare.data.model.ResponseUpdate
import com.capstone.maternalcare.data.model.UserRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("add-user")
    fun register(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @POST("users/changePassword")
    fun checkPassword(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<ResponseData>

    @POST("email")
    fun checkEmail(
        @Query("email") email: String,
    ): Call<ResponseData>

    @PUT("users/{id}")
    fun updateUser(
        @Path("id") id: Int,
        @Query("username") username: String,
        @Query("full_name") full_name: String,
        @Query("age") age: Int,
        @Query("upper_blood_pressure") upper_blood_pressure: Int,
        @Query("lower_blood_pressure") lower_blood_pressure: Int,
        @Query("blood_sugar_level") blood_sugar_level: Float,
        @Query("body_temperature") body_temperature: Float,
        @Query("heart_rate") heart_rate: Int,
    ): Call<ResponseUpdate>

    @GET("users/username")
    fun checkUser(
        @Query("username") username: String,
    ): Call<ResponseData>

    @GET("reset-password")
    fun resetPassword(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("confirm_password") confirm_password: String,
    ): Call<ResponseData>

}