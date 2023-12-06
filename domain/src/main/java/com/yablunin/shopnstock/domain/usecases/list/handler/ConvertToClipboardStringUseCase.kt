package com.yablunin.shopnstock.domain.usecases.list.handler

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.repositories.ListRepository

class ConvertToClipboardStringUseCase(private val listRepository: ListRepository) {
    fun execute(list: ShoppingList, user: User): String{
        return listRepository.toClipboardString(list, user)
    }
}