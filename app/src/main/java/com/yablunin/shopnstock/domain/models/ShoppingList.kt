package com.yablunin.shopnstock.domain.models

import java.io.Serializable

data class ShoppingList(val id: Int, val name: String) : Serializable {

    constructor(): this(-1, "")

    val list = mutableListOf<ListItem>()
}
