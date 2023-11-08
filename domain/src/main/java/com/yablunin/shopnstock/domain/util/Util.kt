package com.yablunin.shopnstock.domain.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

interface Initiable {
    fun init()
}

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