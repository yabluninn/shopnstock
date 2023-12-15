package com.yablunin.shopnstock.domain.usecases.config

import com.yablunin.shopnstock.domain.models.Configuration
import com.yablunin.shopnstock.domain.repositories.ConfigRepository

class LoadConfigUseCase(private val configRepository: ConfigRepository) {
    fun execute(): Configuration{
        return configRepository.load()
    }
}