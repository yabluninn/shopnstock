package com.yablunin.shopnstock.presentation.viewmodels

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.usecases.list.handler.AddListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GenerateListIdUseCase
import com.yablunin.shopnstock.domain.usecases.user.ChangeUsernameUseCase
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase
import com.yablunin.shopnstock.presentation.toasts.ErrorToast
import com.yablunin.shopnstock.presentation.toasts.SuccessfulToast

class HomeViewModel(
    private val auth: FirebaseAuth,
    private val saveUserUseCase: SaveUserUseCase,
    private val addListUseCase: AddListUseCase,
    private val generateListIdUseCase: GenerateListIdUseCase,
    private val loadUserUseCase: LoadUserUseCase,
    private val changeUsernameUseCase: ChangeUsernameUseCase
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

    fun changeUsername(newName: String, user: User){
        changeUsernameUseCase.execute(newName, user)
    }

    fun updatePassword(newPassword: String, user: User, context: Context){
        auth.currentUser?.updatePassword(newPassword)?.addOnCompleteListener {
            if (it.isSuccessful){
                user.password = newPassword
                val successfulToast = SuccessfulToast(
                    context,
                    context.getString(R.string.successful_update_password),
                    Toast.LENGTH_LONG,
                    Gravity.BOTTOM
                )
                successfulToast.show()
                saveUser()
            }
            else{
                val errorToast = ErrorToast(
                    context,
                    it.exception?.message.toString(),
                    Toast.LENGTH_LONG,
                    Gravity.BOTTOM
                )
                errorToast.show()
            }
        }
    }
}