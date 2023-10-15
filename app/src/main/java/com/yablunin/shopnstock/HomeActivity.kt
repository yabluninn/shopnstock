package com.yablunin.shopnstock

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yablunin.shopnstock.list.ShoppingList
import com.yablunin.shopnstock.list.ShoppingListHandler
import com.yablunin.shopnstock.user.User

class HomeActivity : AppCompatActivity() {

    private val user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val newListButton: FloatingActionButton = findViewById(R.id.home_create_task_button)

        newListButton.setOnClickListener{
            showCreateListDialog()
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
                    if (user != null) {
                        ShoppingListHandler.addList(list, user)
                    }
                }
            }
        }
    }
}