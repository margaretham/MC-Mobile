package com.capstone.maternalcare.data.model

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("predicted_risk")
	val predictedRisk: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
