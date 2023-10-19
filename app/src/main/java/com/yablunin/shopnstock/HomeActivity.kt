package com.yablunin.shopnstock

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yablunin.shopnstock.list.ShoppingList
import com.yablunin.shopnstock.list.ShoppingListAdapter
import com.yablunin.shopnstock.list.ShoppingListHandler
import com.yablunin.shopnstock.user.User
import com.yablunin.shopnstock.util.DatabaseHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var dbReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        dbReference = FirebaseDatabase.getInstance().getReference(DatabaseHandler.DB_USERS_NAME)
        var firebaseAuth = FirebaseAuth.getInstance()

        runBlocking {
            launch {
                loadUserData(firebaseAuth)
            }
        }

        val newListButton: FloatingActionButton = findViewById(R.id.home_create_task_button)
        newListButton.setOnClickListener{
            showCreateListDialog()
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
        val usernameText: TextView = findViewById(R.id.home_user_username)
        val emailText: TextView = findViewById(R.id.home_user_email)
        val nothingBackground: LinearLayout = findViewById(R.id.home_nothing_obj)

        val rcView: RecyclerView = findViewById(R.id.home_lists_rc_view)

        usernameText.text = user.username
        emailText.text = user.email

        if (user.shoppingLists.size > 0){
            nothingBackground.visibility = View.GONE
            rcView.visibility = View.VISIBLE
            rcView.layoutManager = LinearLayoutManager(this)
            val adapter = ShoppingListAdapter(this, user.shoppingLists)
            rcView.adapter = adapter
        }
        else{
            nothingBackground.visibility = View.VISIBLE
            rcView.visibility = View.GONE
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
                    val list = ShoppingList(listName)
                    val nothingBackground: LinearLayout = findViewById(R.id.home_nothing_obj)
                    val rcView: RecyclerView = findViewById(R.id.home_lists_rc_view)
                    ShoppingListHandler.addList(list, user)
                    DatabaseHandler.save(dbReference, user)
                    rcView.visibility = View.VISIBLE
                    rcView.layoutManager = LinearLayoutManager(this)
                    val adapter = ShoppingListAdapter(this, user.shoppingLists)
                    rcView.adapter = adapter

                    nothingBackground.visibility = View.GONE
                }
            }
        }
    }
}