package com.capstone.maternalcare.data.model

import com.google.gson.annotations.SerializedName

data class PredictRequest(

	@field:SerializedName("Temperatur tubuh (F)")
	val temperaturTubuhF: Any? = null,

	@field:SerializedName("Tekanan Sistolik (mmHg)")
	val tekananSistolikMmHg: Int? = null,

	@field:SerializedName("Gula darah")
	val gulaDarah: Float? = null,

	@field:SerializedName("Umur (Tahun)")
	val umurTahun: Int? = null,

	@field:SerializedName("Tekanan Diastolik (mmHg)")
	val tekananDiastolikMmHg: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("Detak Jantung")
	val detakJantung: Int? = null
)
