package com.yablunin.shopnstock.domain.models

import java.io.Serializable

data class User(var id: String, var username: String, var email: String, var password: String) : Serializable{
    constructor() : this("", "", "", "")
    val shoppingLists = mutableListOf<com.yablunin.shopnstock.domain.models.ShoppingList>()
}
