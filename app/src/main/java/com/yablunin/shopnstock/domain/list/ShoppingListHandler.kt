package com.yablunin.shopnstock.domain.list

import com.yablunin.shopnstock.domain.user.User
import kotlin.random.Random

class ShoppingListHandler {
    companion object{
        fun addList(list: ShoppingList, user: User){
            user.shoppingLists.add(list)
        }

        fun removeList(list: ShoppingList, user: User){
            user.shoppingLists.remove(list)
        }

        fun getListById(user: User, id: Int): ShoppingList?{
            return user.shoppingLists.find {it.id == id}
        }

        fun generateListId(user: User): Int{
            val randomId: Int = Random.nextInt(0, 100000)
            if (getListById(user, randomId) == null){
                return randomId
            }
            else{
                return generateListId(user)
            }
        }
    }
}