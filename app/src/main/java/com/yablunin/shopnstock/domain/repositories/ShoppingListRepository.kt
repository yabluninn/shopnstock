package com.yablunin.shopnstock.domain.repositories

import com.yablunin.shopnstock.domain.models.ListItem
import com.yablunin.shopnstock.domain.models.ShoppingList

class ShoppingListRepository: ListRepository {
    override fun addItem(list: ShoppingList, item: ListItem) {
        list.list.add(item)
    }

    override fun removeItem(list: ShoppingList, item: ListItem) {
        list.list.remove(item)
    }

    override fun removeItemAt(list: ShoppingList, index: Int) {
        list.list.removeAt(index)
    }

    override fun getItemById(list: ShoppingList, id: Int): ListItem? {
        return list.list.find { it.id == id }
    }

    override fun getItemByIndex(list: ShoppingList, index: Int): ListItem {
        return list.list[index]
    }

    override fun getCompletedItemsCount(list: ShoppingList): Int {
        var count = 0;
        list.list.forEach{
            if (it.isCompleted){
                count++
            }
        }
        return count;
    }

    override fun size(list: ShoppingList): Int {
        return list.list.size
    }
}