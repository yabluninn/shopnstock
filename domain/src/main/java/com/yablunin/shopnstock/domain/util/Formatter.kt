package com.yablunin.shopnstock.domain.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

abstract class Formatter{
    companion object{
        fun formatDate(day: Int, month: Int, year: Int): String {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.set(year, month - 1, day)
            return sdf.format(calendar.time)
        }
    }
}