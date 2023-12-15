package com.yablunin.shopnstock.domain.usecases.list

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.repositories.ListRepository

class DeletePurchasedItemsUseCase(private val listRepository: ListRepository) {
    fun execute(list: ShoppingList){
        listRepository.deletePurchasedItems(list)
    }
}