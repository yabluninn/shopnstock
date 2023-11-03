package com.yablunin.shopnstock.presentation.activities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.data.repository.FirebaseUserRepository
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.presentation.adapters.ShoppingListAdapter
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.databinding.ActivityHomeBinding
import com.yablunin.shopnstock.domain.repositories.ShoppingListHandlerRepository
import com.yablunin.shopnstock.domain.usecases.list.handler.AddListUseCase
import com.yablunin.shopnstock.domain.usecases.list.handler.GenerateListIdUseCase
import com.yablunin.shopnstock.domain.usecases.user.LoadUserUseCase
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var user: User
    private lateinit var dbReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    private val saveUserUseCase = SaveUserUseCase(FirebaseUserRepository())

    private val addListUseCase = AddListUseCase(ShoppingListHandlerRepository())
    private val generateListIdUseCase = GenerateListIdUseCase(ShoppingListHandlerRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
    }

    private fun init(){
        dbReference = FirebaseDatabase.getInstance().getReference(FirebaseUserRepository.DB_USERS_NAME)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.homeCreateTaskButton.setOnClickListener{
            showCreateListDialog()
        }

        runBlocking {
            launch {
                loadUserData(firebaseAuth)
            }
        }
    }

    private fun loadUserData(auth: FirebaseAuth){
        val userRepository = FirebaseUserRepository()
        val loadUserUseCase = LoadUserUseCase(userRepository)
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            loadUserUseCase.execute(userId){ _user ->
                if (_user == null){

                }
                else{
                    user = _user
                    applyDataToUI(user)
                }
            }
        }
    }

    private fun applyDataToUI(user: User){
        binding.homeUserUsername.text = user.username
        binding.homeUserEmail.text = user.email

        if (user.shoppingLists.size > 0){
            binding.homeNothingObj.visibility = View.GONE
            binding.homeListsRcView.visibility = View.VISIBLE
            binding.homeListsRcView.layoutManager = LinearLayoutManager(this)
            val adapter = ShoppingListAdapter(this, user.shoppingLists, user)
            binding.homeListsRcView.adapter = adapter
        }
        else{
            binding.homeNothingObj.visibility = View.VISIBLE
            binding.homeListsRcView.visibility = View.GONE
        }
    }

    private fun showCreateListDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_dialog_create_list)
        dialog.show()
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val drawable = ColorDrawable(Color.TRANSPARENT)
        window?.setBackgroundDrawable(drawable)
        window?.attributes?.windowAnimations = R.style.BottomSheetAnimation
        window?.setGravity(Gravity.BOTTOM)

        val createNewListButton: Button? = dialog.findViewById(R.id.create_list_button)
        val createNewListInput: EditText? = dialog.findViewById(R.id.create_list_name_input)
        createNewListButton?.setOnClickListener {
            dialog.dismiss()
            if (createNewListInput != null) {
                if (!createNewListInput.text.trim().isEmpty()){
                    val listName: String = createNewListInput.text.trim().toString()
                    val listId = generateListIdUseCase.execute(user)
                    val list = ShoppingList(listId, listName)

                    val nothingBackground: LinearLayout = findViewById(R.id.home_nothing_obj)
                    val rcView: RecyclerView = findViewById(R.id.home_lists_rc_view)

                    addListUseCase.execute(list, user)

                    saveUserUseCase.execute(user)
                    rcView.visibility = View.VISIBLE
                    rcView.layoutManager = LinearLayoutManager(this)
                    val adapter = ShoppingListAdapter(this, user.shoppingLists, user)
                    rcView.adapter = adapter

                    nothingBackground.visibility = View.GONE
                }
            }
        }
    }

//    @SuppressLint("CommitTransaction")
//    private fun showUserMenu(settingsLayout: FrameLayout){
//        val menuPopup = Dialog(this)
//        menuPopup.setContentView(R.layout.user_menu_popup)
//        menuPopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        val layoutParams = menuPopup.window?.attributes
//        layoutParams?.gravity = Gravity.END or Gravity.TOP
//        menuPopup.window?.attributes = layoutParams
//
//        menuPopup.show()
//
//        val settingsOptionButton: LinearLayout = menuPopup.findViewById(R.id.user_menu_settings_option)
//        settingsOptionButton.setOnClickListener {
//            menuPopup.dismiss()
//
//            settingsLayout.visibility = View.VISIBLE
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.home_user_settings_fragment_holder, UserSettings.newInstance()).commit()
//        }
//    }
}