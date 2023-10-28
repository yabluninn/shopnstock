package com.yablunin.shopnstock.domain.list

interface ShoppingListRepository {
    fun addItem(item: ListItem)
    fun removeItem(item: ListItem)
    fun removeItemAt(index: Int)
    fun getItemById(id: Int): ListItem?
    fun getItemByIndex(index: Int): ListItem
    fun getCompletedItemsCount(): Int
    fun size(): Int
}