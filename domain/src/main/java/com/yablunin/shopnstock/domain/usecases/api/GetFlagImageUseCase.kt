package com.yablunin.shopnstock.domain.usecases.api

import android.graphics.Bitmap
import com.yablunin.shopnstock.domain.repositories.FlagsRepository

class GetFlagImageUseCase(private val flagsRepository: FlagsRepository) {
    suspend fun execute(countryCode: String): Bitmap?{
        return flagsRepository.getFlagImage(countryCode)
    }
}