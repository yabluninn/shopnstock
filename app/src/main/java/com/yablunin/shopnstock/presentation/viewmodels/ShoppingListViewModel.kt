package com.yablunin.shopnstock.presentation.viewmodels

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.domain.constants.ListConstants
import com.yablunin.shopnstock.domain.models.ListItem
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.usecases.list.AddItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetCompletedItemsCountUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetSizeUseCase
import com.yablunin.shopnstock.domain.usecases.list.RemoveItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.AddListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.CopyListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GetListByIdUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.RemoveListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.RenameListUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase
import com.yablunin.shopnstock.presentation.activities.HomeActivity
import com.yablunin.shopnstock.presentation.toasts.SuccessfulToast

class ShoppingListViewModel(
    private val saveUserUseCase: SaveUserUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val removeItemUseCase: RemoveItemUseCase,
    private val getSizeUseCase: GetSizeUseCase,
    private val getCompletedItemsCountUseCase: GetCompletedItemsCountUseCase,
    private val removeListUseCase: RemoveListUseCase,
    private val getListByIdUseCase: GetListByIdUseCase,
    private val renameListUseCase: RenameListUseCase,
    private val copyListUseCase: CopyListUseCase,
    private val addListUseCase: AddListUseCase
): ViewModel() {

    private val mutableListData = MutableLiveData<ShoppingList>()
    private val mutableListSizeData = MutableLiveData<Int>()
    private val mutableCompletedItemsCountData = MutableLiveData<Int>()
    val listData: LiveData<ShoppingList> = mutableListData
    val listSizeData: LiveData<Int> = mutableListSizeData
    val completedItemsCountData: LiveData<Int> = mutableCompletedItemsCountData

    override fun onCleared() {
        super.onCleared()
    }

    fun showHomeActivity(context: Context){
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
    }

    fun getListById(user: User, defaultId: Int, intent: Intent){
        mutableListData.value = getListByIdUseCase.execute(user, intent.getIntExtra("list_id", defaultId))!!
    }

    fun getListSize(){
        mutableListSizeData.value = getSizeUseCase.execute(listData.value!!)
    }

    fun getCompletedItemsCount(){
        mutableCompletedItemsCountData.value = getCompletedItemsCountUseCase.execute(listData.value!!)
    }

    fun addItem(name: String, quantity: Int, price: Double, unit: String, expirationDate: String, user: User, context: Context){
        val itemId = listSizeData.value!!
        val item =
            ListItem(
                itemId,
                name,
                quantity,
                price,
                unit,
                expirationDate
            )

        addItemUseCase.execute(listData.value!!, item)
        saveUser(user)

        val successfulToast = SuccessfulToast(
            context,
            context.getString(R.string.successful_add_item),
            Toast.LENGTH_LONG,
            Gravity.TOP
        )
        successfulToast.show()
    }

    fun saveUser(user: User){
        saveUserUseCase.execute(user)
    }

    fun removeList(user: User, context: Context){
        removeListUseCase.execute(listData.value!!, user)
        saveUser(user)
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
    }

    fun removeItem(item: ListItem, user: User){
        removeItemUseCase.execute(listData.value!!, item)
        saveUser(user)
    }

    fun renameList(newName: String, user: User){
        renameListUseCase.execute(listData.value!!, newName, user)
        saveUser(user)
    }

    fun copyList(copyAction: Int, list: ShoppingList, user: User, context: Context){
        val newList = copyListUseCase.execute(copyAction, list, user)
        addListUseCase.execute(newList, user)
        saveUser(user)
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
    }
}