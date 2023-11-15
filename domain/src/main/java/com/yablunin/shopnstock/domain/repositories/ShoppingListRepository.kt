package com.yablunin.shopnstock.domain.repositories

import com.yablunin.shopnstock.domain.models.ListItem
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import java.lang.StringBuilder

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
            if (it.purchased){
                count++
            }
        }
        return count;
    }

    override fun size(list: ShoppingList): Int {
        return list.list.size
    }

    override fun toClipboardString(list: ShoppingList, user: User): String {
        val formattedList = StringBuilder()
        formattedList.append("${list.name} by ${user.username}\n")
        for (item in list.list){
            formattedList.append(item.toString() + "\n")
        }
        return formattedList.toString()
    }
}