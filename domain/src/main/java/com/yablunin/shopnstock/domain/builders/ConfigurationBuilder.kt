package com.yablunin.shopnstock.domain.builders

import com.yablunin.shopnstock.domain.constants.Languages
import com.yablunin.shopnstock.domain.enums.AppTheme
import com.yablunin.shopnstock.domain.enums.SortingOrder
import com.yablunin.shopnstock.domain.models.Configuration

class ConfigurationBuilder {
    // App config
    private var theme: AppTheme = AppTheme.THEME_LIGHT
    private var language: String = Languages.ENGLISH

    // User config
    private var passwordRecoveryEnabled: Boolean = true
    private var usernameChangeEnabled: Boolean = true

    // Notifications config
    private var notificationsEnabled: Boolean = true

    // Lists config
    private var listsSortingOrder: SortingOrder = SortingOrder.SORT_BY_DATE_CREATED

    fun theme(value: AppTheme){
        theme = value
    }
    fun language(value: String){
        language = value
    }
    fun passwordRecoveryEnabled(value: Boolean){
        passwordRecoveryEnabled = value
    }
    fun usernameChangeEnabled(value: Boolean){
        usernameChangeEnabled = value
    }
    fun notificationsEnabled(value: Boolean){
        notificationsEnabled = value
    }
    fun listsSortingOrder(value: SortingOrder){
        listsSortingOrder = value
    }

    fun build(): Configuration{
        return Configuration(
            theme = theme,
            language = language,
            passwordRecoveryEnabled = passwordRecoveryEnabled,
            usernameChangeEnabled = usernameChangeEnabled,
            notificationsEnabled = notificationsEnabled,
            listsSortingOrder = listsSortingOrder
        )
    }
}