package com.yablunin.shopnstock.domain.repositories

import com.yablunin.shopnstock.domain.models.Configuration

interface ConfigRepository {
    fun save(configuration: Configuration)
    fun load(): Configuration
}