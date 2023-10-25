package com.yablunin.shopnstock.list

import java.io.Serializable

data class ShoppingList(val id: Int, val name: String) : Serializable {

    constructor(): this(-1, "")

    val list = mutableListOf<ListItem>() // Non private because to save it and serialize this field must be public

    fun add(item: ListItem){
        list.add(item)
    }

    fun remove(item: ListItem){
        list.remove(item)
    }

    fun removeAt(itemIndex: Int){
        list.removeAt(itemIndex)
    }

    fun getById(itemId: Int): ListItem? {
        return list.find { it.id == itemId }
    }

    fun getByIndex(itemIndex: Int): ListItem {
        return list[itemIndex]
    }

    fun getCompletedItemsCount(): Int{
        var count = 0;
        list.forEach{
            if (it.isCompleted){
                count++
            }
        }
        return count;
    }

    fun size(): Int{
        return list.size
    }
}
