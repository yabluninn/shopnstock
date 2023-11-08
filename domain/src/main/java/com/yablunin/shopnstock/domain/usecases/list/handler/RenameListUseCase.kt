package com.yablunin.shopnstock.domain.usecases.list.handler

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.repositories.ShoppingListHandlerRepository

class RenameListUseCase(private val shoppingListHandlerRepository: ShoppingListHandlerRepository) {
    fun execute(list: ShoppingList, newName: String, user: User){
        shoppingListHandlerRepository.renameList(list, newName, user)
    }
}