package com.yablunin.shopnstock.domain.usecases.list

import com.yablunin.shopnstock.domain.models.ListItem
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.repositories.ListRepository

class GetItemByIndexUseCase(private val listRepository: ListRepository) {
    fun execute(list: ShoppingList, index: Int): ListItem{
        return listRepository.getItemByIndex(list, index)
    }
}