package com.yablunin.shopnstock.domain.repositories

import android.graphics.Bitmap
import com.yablunin.shopnstock.domain.models.ListItem
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User


interface ListRepository {
    fun addItem(list: ShoppingList, item: ListItem)
    fun removeItem(list: ShoppingList, item: ListItem)
    fun removeItemAt(list: ShoppingList, index: Int)
    fun getItemById(list: ShoppingList, id: Int): ListItem?
    fun getItemByIndex(list: ShoppingList, index: Int): ListItem
    fun getCompletedItemsCount(list: ShoppingList): Int
    fun size(list: ShoppingList): Int
    fun toClipboardString(list: ShoppingList, user: User): String
    fun generateQRCodeBitmap(list: ShoppingList): Bitmap
    fun getTotalPrice(list: ShoppingList): Double
    fun deletePurchasedItems(list: ShoppingList)
    fun uncheckAllItems(list: ShoppingList)
}