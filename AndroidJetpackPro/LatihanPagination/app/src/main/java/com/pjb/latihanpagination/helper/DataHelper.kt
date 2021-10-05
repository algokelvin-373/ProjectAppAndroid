package com.pjb.latihanpagination.helper

import java.text.SimpleDateFormat
import java.util.*

object DataHelper {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}