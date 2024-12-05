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


data class ResponseAllHistory (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : ArrayList<History>? = null
)

data class ResponseHistory (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : History? = null
)