package com.yablunin.shopnstock.domain.list

import java.io.Serializable

data class ShoppingList(val id: Int, val name: String) : Serializable, ShoppingListRepository {

    constructor(): this(-1, "")

    val list = mutableListOf<ListItem>() // Non private because to save it and serialize this field must be public

    override fun addItem(item: ListItem) {
        list.add(item)
    }

    override fun removeItem(item: ListItem) {
        list.remove(item)
    }

    override fun removeItemAt(index: Int) {
        list.removeAt(index)
    }

    override fun getItemById(id: Int): ListItem? {
        return list.find { it.id == id }
    }

    override fun getItemByIndex(index: Int): ListItem {
        return list[index]
    }

    override fun getCompletedItemsCount(): Int{
        var count = 0;
        list.forEach{
            if (it.isCompleted){
                count++
            }
        }
        return count;
    }

    override fun size(): Int{
        return list.size
    }
}
