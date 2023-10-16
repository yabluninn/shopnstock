package com.yablunin.shopnstock.list

data class ShoppingList(val name: String){

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
}
