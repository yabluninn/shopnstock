package com.yablunin.shopnstock.presentation.activities

import android.annotation.SuppressLint
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
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.domain.list.ShoppingList
import com.yablunin.shopnstock.presentation.adapters.ShoppingListAdapter
import com.yablunin.shopnstock.domain.list.ShoppingListHandler
import com.yablunin.shopnstock.domain.user.User
import com.yablunin.shopnstock.data.DatabaseHandler
import com.yablunin.shopnstock.databinding.ActivityHomeBinding
import com.yablunin.shopnstock.presentation.fragments.UserSettings
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var user: User
    private lateinit var dbReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbReference = FirebaseDatabase.getInstance().getReference(DatabaseHandler.DB_USERS_NAME)
        var firebaseAuth = FirebaseAuth.getInstance()

        runBlocking {
            launch {
                loadUserData(firebaseAuth)
            }
        }

        binding.homeCreateTaskButton.setOnClickListener{
            showCreateListDialog()
        }

        val settingsFragment = binding.homeUserSettingsFragmentHolder
        settingsFragment.visibility = View.GONE

        binding.homeMenuButton.setOnClickListener {
            showUserMenu(settingsFragment)
        }
    }

    private fun loadUserData(auth: FirebaseAuth){
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            val database = FirebaseDatabase.getInstance()
            val userRef = database.reference.child("users").child(userId)

            DatabaseHandler.load(userRef) { _user ->
                if (_user != null) {
                    user = _user

                    applyDataToUI(user)
                }
                else {
                    // Пользователь не найден в базе данных
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
                    val list = ShoppingList(ShoppingListHandler.generateListId(user), listName)
                    val nothingBackground: LinearLayout = findViewById(R.id.home_nothing_obj)
                    val rcView: RecyclerView = findViewById(R.id.home_lists_rc_view)
                    ShoppingListHandler.addList(list, user)
                    DatabaseHandler.save(dbReference, user)
                    rcView.visibility = View.VISIBLE
                    rcView.layoutManager = LinearLayoutManager(this)
                    val adapter = ShoppingListAdapter(this, user.shoppingLists, user)
                    rcView.adapter = adapter

                    nothingBackground.visibility = View.GONE
                }
            }
        }
    }

    @SuppressLint("CommitTransaction")
    private fun showUserMenu(settingsLayout: FrameLayout){
        val menuPopup = Dialog(this)
        menuPopup.setContentView(R.layout.user_menu_popup)
        menuPopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val layoutParams = menuPopup.window?.attributes
        layoutParams?.gravity = Gravity.END or Gravity.TOP
        menuPopup.window?.attributes = layoutParams

        menuPopup.show()

        val settingsOptionButton: LinearLayout = menuPopup.findViewById(R.id.user_menu_settings_option)
        settingsOptionButton.setOnClickListener {
            menuPopup.dismiss()

            settingsLayout.visibility = View.VISIBLE
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.home_user_settings_fragment_holder, UserSettings.newInstance()).commit()
        }
    }
}