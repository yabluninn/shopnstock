package com.yablunin.shopnstock.domain.usecases.list

import com.yablunin.shopnstock.domain.models.ListItem
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.repositories.ListRepository

class GetItemByIdUseCase(private val listRepository: ListRepository) {
    fun execute(list: ShoppingList, id: Int): ListItem?{
        return listRepository.getItemById(list, id)
    }
}