package com.yablunin.shopnstock.domain.repositories

import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import kotlin.random.Random

class ShoppingListHandlerRepository: ListHandlerRepository {
    override fun addList(list: ShoppingList, user: User) {
        user.shoppingLists.add(list)
    }

    override fun removeList(list: ShoppingList, user: User) {
        user.shoppingLists.remove(list)
    }

    override fun getListById(user: User, id: Int): ShoppingList? {
        return user.shoppingLists.find {it.id == id}
    }

    override fun generateListId(user: User): Int {
        val randomId: Int = Random.nextInt(0, 100000)
        if (getListById(user, randomId) == null){
            return randomId
        }
        else{
            return generateListId(user)
        }
    }

    override fun renameList(list: ShoppingList, newName: String, user: User) {
        user.shoppingLists.find { it.id == list.id }!!.name = newName
    }

}