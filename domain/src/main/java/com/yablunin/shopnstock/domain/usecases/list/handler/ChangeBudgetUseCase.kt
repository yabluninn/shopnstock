package com.yablunin.shopnstock.domain.usecases.list.handler

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.repositories.ListHandlerRepository

class ChangeBudgetUseCase(private val listHandlerRepository: ListHandlerRepository) {
    fun execute(list: ShoppingList, newBudget: Double){
        listHandlerRepository.changeBudget(list, newBudget)
    }
}