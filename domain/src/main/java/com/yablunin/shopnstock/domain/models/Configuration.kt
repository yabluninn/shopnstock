package com.yablunin.shopnstock.domain.models

import com.yablunin.shopnstock.domain.enums.AppTheme
import com.yablunin.shopnstock.domain.enums.SortingOrder

class Configuration(
    // App config
    val theme: AppTheme,
    val language: String,

    // User config
    val passwordRecoveryEnabled: Boolean,
    val usernameChangeEnabled: Boolean,

    // Notifications config
    val notificationsEnabled: Boolean,

    // Lists config
    var listsSortingOrder: SortingOrder
)