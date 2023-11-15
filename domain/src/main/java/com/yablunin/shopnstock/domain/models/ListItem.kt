package com.yablunin.shopnstock.domain.models

import java.io.Serializable

data class ListItem(val id: Int, val name: String, val quantity: Int, val price: Double, val unit: String, val expirationDate: String): Serializable{
    constructor(): this(-1, "", 0, 0.0, "", "")
    var purchased: Boolean = false;

    override fun toString(): String {
        return "${this.name}: ${this.quantity} ${this.unit}. Price: ${this.price}, Use before: ${this.expirationDate}"
    }
}
