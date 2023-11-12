package com.yablunin.shopnstock.domain.usecases.list.handler

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.repositories.ListHandlerRepository

class CopyListUseCase(private val listHandlerRepository: ListHandlerRepository) {
    fun execute(copyAction: Int, list: ShoppingList, user: User): ShoppingList{
        return listHandlerRepository.copyList(copyAction, list, user)
    }
}