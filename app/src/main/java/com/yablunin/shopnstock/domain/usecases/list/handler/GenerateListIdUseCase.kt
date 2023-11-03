package com.yablunin.shopnstock.domain.usecases.list.handler

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.repositories.ListHandlerRepository

class GenerateListIdUseCase(private val listHandlerRepository: ListHandlerRepository) {
    fun execute(user: User) : Int{
        return listHandlerRepository.generateListId(user)
    }
}