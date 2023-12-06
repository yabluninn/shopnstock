package com.yablunin.shopnstock.domain.usecases.list.handler

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.repositories.ListHandlerRepository

class RenameListUseCase(private val listHandlerRepository: ListHandlerRepository) {
    fun execute(list: ShoppingList, newName: String, user: User){
        listHandlerRepository.renameList(list, newName, user)
    }
}