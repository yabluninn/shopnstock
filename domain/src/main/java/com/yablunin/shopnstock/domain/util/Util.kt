package com.yablunin.shopnstock.domain.util

import android.graphics.Bitmap
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

class Language(
    val languageCode: String,
    val language: String,
    val countryCode: String
)

class Flag(
    val language: Language,
    val bitmap: Bitmap?
)

object LanguageConstants{
    const val ENGLISH = "en"
    const val RUSSIAN = "ru"
    const val UKRAINIAN = "ua"
}