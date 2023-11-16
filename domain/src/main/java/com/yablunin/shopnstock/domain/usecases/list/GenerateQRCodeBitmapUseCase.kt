package com.yablunin.shopnstock.domain.usecases.list

import android.graphics.Bitmap
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.repositories.ListRepository

class GenerateQRCodeBitmapUseCase(private val listRepository: ListRepository) {
    fun execute(list: ShoppingList): Bitmap{
        return listRepository.generateQRCodeBitmap(list)
    }
}