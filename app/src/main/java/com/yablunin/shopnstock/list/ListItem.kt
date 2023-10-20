package com.yablunin.shopnstock.list

import java.io.Serializable

data class ListItem(val id: Int, val name: String, val quantity: Int, val price: Double, val unit: String, val expirationDate: String): Serializable{
    constructor(): this(-1, "", 0, 0.0, "", "")
    var isCompleted: Boolean = false;
}
