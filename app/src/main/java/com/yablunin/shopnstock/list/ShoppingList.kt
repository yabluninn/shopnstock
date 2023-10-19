package com.yablunin.shopnstock.list

import java.io.Serializable

data class ShoppingList(val name: String) : Serializable {

    constructor(): this("")

    private val list = mutableListOf<ListItem>()

    fun add(item: ListItem){
        list.add(item)
    }

    fun remove(item: ListItem){
        list.remove(item)
    }

    fun removeAt(index: Int){
        list.removeAt(index)
    }

    fun getById(id: Int): ListItem? {
        return list.find { it.id == id }
    }

    fun getByIndex(index: Int): ListItem {
        return list[index]
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
