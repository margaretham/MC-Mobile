package com.capstone.maternalcare.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.Period


const val DEFAULT_QUERY_ARTICLES = "Maternal"
const val DEFAULT_SORT_BY_ARTICLES = "publishedAt"
const val ARTICLE_API_KEY = "....."

fun getPercentage(dayToHeal: Int? = 1, thisDay: Int): Int {
    return ((thisDay.toDouble() / dayToHeal!!.toDouble()) * 100).toInt()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getAges(dateOfBirth: String): Int {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val dateOfBirthParse = dateFormat.parse(dateOfBirth)
    val today = dateFormat.format(Date())
    val todayParse = dateFormat.parse(today)
    var numberOfDays = 0L
    if (todayParse != null && dateOfBirthParse != null) {
        numberOfDays = (todayParse.time - dateOfBirthParse.time) / (1000 * 60 * 60 * 24)
    }

    return numberOfDays.div(365).toInt()
}



fun changeTimeFormatCreatedAt(date: String) : String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateParse = dateFormat.parse(date)
    val dateFormat2 = SimpleDateFormat("dd LLLL yyyy", Locale.getDefault())
    return dateFormat2.format(dateParse!!)
}

fun changeTimeFormat(date: String? = "2022/01/01") : String{
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    val dateParse = dateFormat.parse(date!!)
    val dateFormat2 = SimpleDateFormat("dd LLLL yyyy", Locale.getDefault())
    return dateFormat2.format(dateParse!!)
}

fun changeFormatTime(date:String? = "2022-01-01"): String {
    val dateFormat2 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateParse = dateFormat2.parse(date!!)
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return dateFormat.format(dateParse!!)
}

fun addTime(time: Int): String{
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

    val cal = Calendar.getInstance()
    cal.time = dateFormat.parse(dateFormat.format(Date()))
    cal.add(Calendar.DAY_OF_MONTH , time)

    return dateFormat.format(cal.time)
}

fun todayDate(): String{
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return dateFormat.format(Date())
}

fun addTimes(time: Int, date: String? = "2022-01-01"): String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val cal = Calendar.getInstance()
    cal.time = dateFormat.parse(date)
    cal.add(Calendar.DAY_OF_MONTH , time)

    return dateFormat.format(cal.time)
}

interface ResponseCallback {
    fun getCallback(msg:String,status: Boolean)
}

fun dateFormat(dateString: String?): String {
    return "${dateString?.substring(0,10)} ${dateString?.substring(12,19)}"
}
