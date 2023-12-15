package com.yablunin.shopnstock.domain.repositories

import android.graphics.Bitmap

interface FlagsRepository {
    suspend fun getFlagImage(countryCode: String): Bitmap?
}