package com.yablunin.shopnstock.domain.models

import com.yablunin.shopnstock.domain.enums.AppTheme
import com.yablunin.shopnstock.domain.enums.SortingOrder

class Configuration(
    // App config
    var theme: AppTheme,
    val language: String,

    // User config
    val passwordRecoveryEnabled: Boolean,
    val usernameChangeEnabled: Boolean,

    // Notifications config
    val notificationsEnabled: Boolean,

    // Lists config
    var listsSortingOrder: SortingOrder
){
    companion object{
        const val CONFIG_SHARED_PREFS_NAME = "app_config"
        const val CONFIG_SHARED_PREFS_KEY = "config"
    }
}