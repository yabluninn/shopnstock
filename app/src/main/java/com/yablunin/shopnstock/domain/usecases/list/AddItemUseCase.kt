package com.yablunin.shopnstock.domain.usecases.list

import com.yablunin.shopnstock.domain.models.ListItem
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.repositories.ListRepository

class AddItemUseCase(private val listRepository: ListRepository) {
    fun execute(list: ShoppingList, item: ListItem){
        listRepository.addItem(list, item)
    }
}