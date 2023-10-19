package com.yablunin.shopnstock

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.yablunin.shopnstock.list.ShoppingList
import com.yablunin.shopnstock.user.User
import com.yablunin.shopnstock.util.DatabaseHandler
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ShoppingListActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)

        val listName: TextView = findViewById(R.id.shopping_list_name_text)
        val listItemsCountText: TextView = findViewById(R.id.shopping_list_items_count)

        val backButton: ImageView = findViewById(R.id.shopping_list_back_button)
        val addItemButton: Button = findViewById(R.id.shopping_list_add_item_button)

        val list = intent.getSerializableExtra("list_data", ShoppingList::class.java);

        if (list != null){
            listName.text = list.name
            if (list.size() > 0){
                listItemsCountText.text = "List ${list.getCompletedItemsCount()} / ${list.size()} completed"
            }
            else{
                listItemsCountText.text = "Nothing here"
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        addItemButton.setOnClickListener {
            showAddItemPopup()
        }

    }

    private fun showAddItemPopup(){
        val addItemPopup = Dialog(this)
        addItemPopup.setContentView(R.layout.add_new_item_popup)
        addItemPopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        addItemPopup.show()

        val closeButton: ImageView = addItemPopup.findViewById(R.id.shopping_list_back_button)
        val expirationDateInput: EditText = addItemPopup.findViewById(R.id.add_new_item_input_exp_date)

        closeButton.setOnClickListener {
            addItemPopup.dismiss()
        }

        expirationDateInput.setOnClickListener {
            showSetExpirationDateDialog(expirationDateInput)
        }
    }

    private fun showSetExpirationDateDialog(dateInput: EditText){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = formatDate(selectedDay, selectedMonth + 1, selectedYear)
            dateInput.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun formatDate(day: Int, month: Int, year: Int): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        return sdf.format(calendar.time)
    }

}