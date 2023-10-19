package com.yablunin.shopnstock.list

import com.yablunin.shopnstock.user.User

class ShoppingListHandler {
    companion object{
        fun addList(list: ShoppingList, user: User){
            user.shoppingLists.add(list)
        }

        fun loadLists(user: User){

        }
    }
}