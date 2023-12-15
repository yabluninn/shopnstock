package com.yablunin.shopnstock.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.yablunin.shopnstock.data.services.FlagsApiService
import com.yablunin.shopnstock.domain.repositories.FlagsRepository

class FlagsApiRepository(private val flagsApiService: FlagsApiService): FlagsRepository {
    override suspend fun getFlagImage(countryCode: String): Bitmap? {
        val response = flagsApiService.getFlagImage(countryCode)

        return if (response.isSuccessful){
            response.body()?.byteStream()?.let { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }
        else{
            null
        }
    }
}