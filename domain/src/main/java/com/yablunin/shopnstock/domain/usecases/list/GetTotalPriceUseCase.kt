package com.yablunin.shopnstock.domain.usecases.list

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.repositories.ListRepository

class GetTotalPriceUseCase(private val listRepository: ListRepository) {
    fun execute(list: ShoppingList): Double{
        return listRepository.getTotalPrice(list)
    }
}