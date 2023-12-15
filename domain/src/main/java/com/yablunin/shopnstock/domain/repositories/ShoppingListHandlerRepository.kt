package com.yablunin.shopnstock.domain.repositories

import com.yablunin.shopnstock.domain.constants.ListConstants
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

    override fun copyList(copyAction: Int, list: ShoppingList, user: User): ShoppingList {
        val copiedListName = if (list.name.endsWith("(copy)")) {
            list.name
        } else {
            list.name + " (copy)"
        }
        val copiedList = ShoppingList(generateListId(user), copiedListName, list.budget)
        when(copyAction){
            ListConstants.COPY_WHOLE_LIST -> {
                for (item in list.list){
                    copiedList.list.add(item)
                }
            }
            ListConstants.COPY_PURCHASED_ITEMS_LIST -> {
                for (item in list.list){
                    if (item.purchased){
                        copiedList.list.add(item)
                    }
                }
            }
            ListConstants.COPY_UNPURCHASED_ITEMS_LIST -> {
                for (item in list.list){
                    if (!item.purchased){
                        copiedList.list.add(item)
                    }
                }
            }
        }
        return copiedList
    }

    override fun changeBudget(list: ShoppingList, newBudget: Double) {
        list.budget = newBudget
    }

    override fun renameList(list: ShoppingList, newName: String, user: User) {
        user.shoppingLists.find { it.id == list.id }!!.name = newName
    }



}