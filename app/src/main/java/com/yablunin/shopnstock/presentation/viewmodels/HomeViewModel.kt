package com.yablunin.shopnstock.presentation.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.view.Gravity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.google.firebase.auth.FirebaseAuth

import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.domain.enums.AppTheme
import com.yablunin.shopnstock.domain.models.Configuration
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.usecases.api.GetFlagImageUseCase
import com.yablunin.shopnstock.domain.usecases.config.LoadConfigUseCase
import com.yablunin.shopnstock.domain.usecases.config.SaveConfigUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.AddListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GenerateListIdUseCase
import com.yablunin.shopnstock.domain.usecases.user.ChangeUsernameUseCase
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase
import com.yablunin.shopnstock.domain.util.Flag
import com.yablunin.shopnstock.domain.util.Language
import com.yablunin.shopnstock.presentation.toasts.ErrorToast
import com.yablunin.shopnstock.presentation.toasts.SuccessfulToast
import kotlinx.coroutines.launch
import java.util.Locale

class HomeViewModel(
    private val auth: FirebaseAuth,
    private val saveUserUseCase: SaveUserUseCase,
    private val addListUseCase: AddListUseCase,
    private val generateListIdUseCase: GenerateListIdUseCase,
    private val loadUserUseCase: LoadUserUseCase,
    private val changeUsernameUseCase: ChangeUsernameUseCase,
    private val loadConfigUseCase: LoadConfigUseCase,
    private val saveConfigUseCase: SaveConfigUseCase,
    private val getFlagImageUseCase: GetFlagImageUseCase
): ViewModel() {

    private val mutableUserLiveData = MutableLiveData<User>()
    private val mutableListIdLiveData = MutableLiveData<Int>()
    val userLiveData: LiveData<User> = mutableUserLiveData
    val listIdLiveData: LiveData<Int> = mutableListIdLiveData

    var configuration: Configuration? = null

    val flags = mutableListOf<Flag>()

    var languages = arrayListOf(
        Language(Language.ENGLISH, "English", "GB"),
        Language(Language.RUSSIAN, "Russian", "RU"),
        Language(Language.UKRAINIAN, "Ukrainian", "UA")
    )

    override fun onCleared() {
        super.onCleared()
    }

    fun loadUser(){
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            loadUserUseCase.execute(userId){ user ->
                if (user == null){

                } else{
                    mutableUserLiveData.value = user
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
                SuccessfulToast(
                    context,
                    message = context.getString(R.string.successful_update_password),
                    duration = Toast.LENGTH_LONG,
                    position = Gravity.BOTTOM
                ).show()
                saveUser()
            } else{
                 ErrorToast(
                    context,
                    message = it.exception?.message.toString(),
                    duration = Toast.LENGTH_LONG,
                    position = Gravity.BOTTOM
                ).show()
            }
        }
    }

    private fun saveConfiguration(configuration: Configuration){
        saveConfigUseCase.execute(configuration)
    }

    fun loadConfiguration(){
        configuration = loadConfigUseCase.execute()
    }

    fun enableDarkTheme(enable: Boolean){
        if (enable){
            configuration!!.theme = AppTheme.THEME_DARK
        } else{
            configuration!!.theme = AppTheme.THEME_LIGHT
        }
        saveConfiguration(configuration!!)
    }

    fun setAppLocale(context: Context, languageCode: String){
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources = context.resources
        val config = android.content.res.Configuration(resources.configuration)

        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        configuration!!.language = languageCode
        saveConfiguration(configuration!!)
    }

    fun initFlags(){
        viewModelScope.launch {
            for (language in languages){
                val bitmap = getFlagImage(language.countryCode)
                val flag = Flag(language, bitmap)
                flags.add(flag)
            }
        }
    }

    private suspend fun getFlagImage(countryCode: String): Bitmap?{
        return getFlagImageUseCase.execute(countryCode)
    }

    fun isCurrentLanguage(language: Language): Boolean{
        return language.languageCode == configuration!!.language
    }

    fun getFlagBitmap(language: Language): Bitmap? {
        return flags.find { it.language == language }?.bitmap
    }
}