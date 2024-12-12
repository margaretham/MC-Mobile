package com.capstone.maternalcare.data.model

data class Responses (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
)

data class ResponseData (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : UserLogin? = null
)

data class ResponseUpdate (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : UserUpdate? = null
)


data class RequestData(
    val email: String,
    val age: Int,
    val upperBloodPressure: Int,
    val lowerBloodPressure: Int,
    val bloodSugarLevel: Float,
    val bodyTemperature: Float,
    val heartRate: Int
)

data class ResponseDataRequest(
    val email: String,
    val predictedRisk: String
)
