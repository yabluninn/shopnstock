package com.yablunin.shopnstock.domain.util

class Language(
    val languageCode: String,
    val language: String,
    val countryCode: String
){
    companion object{
        const val ENGLISH = "en"
        const val RUSSIAN = "ru"
        const val UKRAINIAN = "ua"
    }
}