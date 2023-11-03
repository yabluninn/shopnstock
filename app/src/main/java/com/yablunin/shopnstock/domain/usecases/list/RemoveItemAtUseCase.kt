package com.yablunin.shopnstock.domain.usecases.list

import com.yablunin.shopnstock.domain.models.ListItem
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.repositories.ListRepository

class RemoveItemAtUseCase(private val listRepository: ListRepository) {
    fun execute(list: ShoppingList, index: Int){
        listRepository.removeItemAt(list, index)
    }
}