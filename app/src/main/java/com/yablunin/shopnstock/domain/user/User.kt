package com.yablunin.shopnstock.domain.user

import com.yablunin.shopnstock.domain.list.ShoppingList
import java.io.Serializable

data class User(var id: String, var username: String, var email: String, var password: String) : Serializable{
    constructor() : this("", "", "", "")
    val shoppingLists = mutableListOf<ShoppingList>()
}
