package com.yablunin.shopnstock.domain.repositories

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User

interface ListHandlerRepository {
    fun addList(list: ShoppingList, user: User)
    fun removeList(list: ShoppingList, user: User)
    fun renameList(list: ShoppingList, newName: String, user: User)
    fun getListById(user: User, id: Int): ShoppingList?
    fun generateListId(user: User): Int
    fun copyList(copyAction: Int, list: ShoppingList, user: User): ShoppingList
    fun changeBudget(list: ShoppingList, newBudget: Double)
}