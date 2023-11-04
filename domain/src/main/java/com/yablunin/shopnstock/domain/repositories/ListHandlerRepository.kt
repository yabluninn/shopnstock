package com.yablunin.shopnstock.domain.repositories

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User

interface ListHandlerRepository {
    fun addList(list: ShoppingList, user: User)
    fun removeList(list: ShoppingList, user: User)
    fun getListById(user: User, id: Int): ShoppingList?
    fun generateListId(user: User): Int
}