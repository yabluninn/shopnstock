package com.yablunin.shopnstock.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.usecases.list.handler.AddListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GenerateListIdUseCase
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase

class HomeViewModel(
    private val auth: FirebaseAuth,
    private val saveUserUseCase: SaveUserUseCase,
    private val addListUseCase: AddListUseCase,
    private val generateListIdUseCase: GenerateListIdUseCase,
    private val loadUserUseCase: LoadUserUseCase
): ViewModel() {

    private val mutableUserLiveData = MutableLiveData<User>()
    private val mutableListIdLiveData = MutableLiveData<Int>()
    val userLiveData: LiveData<User> = mutableUserLiveData
    val listIdLiveData: LiveData<Int> = mutableListIdLiveData

    override fun onCleared() {
        super.onCleared()
    }

    fun loadUser(){
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            loadUserUseCase.execute(userId){ _user ->
                if (_user == null){

                }
                else{
                    mutableUserLiveData.value = _user
                }
            }
        }
    }

    fun generateListId(){
        mutableListIdLiveData.value = generateListIdUseCase.execute(userLiveData.value!!)
    }

    fun addList(list: ShoppingList){
        addListUseCase.execute(list, userLiveData.value!!)
    }

    fun saveUser(){
        saveUserUseCase.execute(userLiveData.value!!)
    }
}