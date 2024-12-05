package com.capstone.maternalcare.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class History (
    var id_history : Int,
    var day_to_born: String? = null,
    var date_to_born: String? = null,
    var status: String? = null,
    var created_at : String? = null,
    var id_user : Int,
    var id_diagnose : Int,
): Parcelable