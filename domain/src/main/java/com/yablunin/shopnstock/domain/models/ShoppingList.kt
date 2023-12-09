package com.yablunin.shopnstock.domain.models

import java.io.Serializable

data class ShoppingList(val id: Int, var name: String, var budget: Double) : Serializable {

    constructor(): this(-1, "", 0.0)

    val list = mutableListOf<ListItem>()
}
