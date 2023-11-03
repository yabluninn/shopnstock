package com.yablunin.shopnstock.domain.usecases.list.handler

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.repositories.ListHandlerRepository

class RemoveListUseCase(private val listHandlerRepository: ListHandlerRepository) {
    fun execute(list: ShoppingList, user: User){
        listHandlerRepository.removeList(list, user)
    }
}