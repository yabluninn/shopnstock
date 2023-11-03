package com.yablunin.shopnstock.domain.usecases.list

import com.yablunin.shopnstock.domain.models.ListItem
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.repositories.ListRepository

class GetCompletedItemsCountUseCase(private val listRepository: ListRepository) {
    fun execute(list: ShoppingList): Int{
        return listRepository.getCompletedItemsCount(list)
    }
}