package com.yablunin.shopnstock.user

import com.yablunin.shopnstock.list.ListItem
import com.yablunin.shopnstock.list.ShoppingList
import com.yablunin.shopnstock.list.ShoppingListHandler

data class User(val username: String, val email: String, val password: String){

    val shoppingLists = mutableListOf<ShoppingList>()
}
