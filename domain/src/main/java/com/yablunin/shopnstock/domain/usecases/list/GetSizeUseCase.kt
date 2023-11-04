package com.yablunin.shopnstock.domain.usecases.list

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.repositories.ListRepository

class GetSizeUseCase(private val listRepository: ListRepository) {
    fun execute(list: ShoppingList): Int{
        return listRepository.size(list)
    }
}