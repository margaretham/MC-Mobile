package com.capstone.maternalcare.data.api.predict

import com.capstone.maternalcare.data.model.PredictRequest
import com.capstone.maternalcare.data.model.PredictResponse
import com.capstone.maternalcare.data.model.RequestData
import com.capstone.maternalcare.data.model.ResponseDataRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceUser {
    @POST("predict")
    fun sendData(@Body requestData: PredictRequest): Call<PredictResponse>
}
