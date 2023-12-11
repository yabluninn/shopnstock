package com.yablunin.shopnstock.domain.usecases.config

import com.yablunin.shopnstock.domain.models.Configuration
import com.yablunin.shopnstock.domain.repositories.ConfigRepository

class SaveConfigUseCase(private val configRepository: ConfigRepository) {
    fun execute(configuration: Configuration){
        configRepository.save(configuration)
    }
}