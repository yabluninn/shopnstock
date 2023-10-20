package com.yablunin.shopnstock.user

import com.yablunin.shopnstock.list.ListItem
import com.yablunin.shopnstock.list.ShoppingList
import com.yablunin.shopnstock.list.ShoppingListHandler
import java.io.Serializable


data class User(var id: String, var username: String, var email: String, var password: String) : Serializable{
    constructor() : this("", "", "", "")
    val shoppingLists = mutableListOf<ShoppingList>()
}
