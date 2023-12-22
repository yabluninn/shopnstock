package com.yablunin.shopnstock.data.repositories

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.google.gson.Gson
import com.yablunin.shopnstock.domain.builders.ConfigurationBuilder
import com.yablunin.shopnstock.domain.models.Configuration
import com.yablunin.shopnstock.domain.repositories.ConfigRepository

class SharedPrefsConfigRepository(val sharedPreferences: SharedPreferences): ConfigRepository {
    private val gson = Gson()
    @SuppressLint("CommitPrefEdits")
    override fun save(configuration: Configuration) {
        val json = gson.toJson(configuration)
        sharedPreferences.edit().putString(Configuration.CONFIG_SHARED_PREFS_KEY, json).apply()
    }

    override fun load(): Configuration {
        val json = sharedPreferences.getString(Configuration.CONFIG_SHARED_PREFS_KEY, null)
        return if (json != null){
            gson.fromJson(json, Configuration::class.java)
        } else{
            ConfigurationBuilder().build()
        }
    }
}