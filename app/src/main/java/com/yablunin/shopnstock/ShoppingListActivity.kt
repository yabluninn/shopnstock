package com.yablunin.shopnstock

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.yablunin.shopnstock.list.ListItem
import com.yablunin.shopnstock.list.ShoppingList
import com.yablunin.shopnstock.list.ShoppingListHandler
import com.yablunin.shopnstock.list.ShoppingListItemsAdapter
import com.yablunin.shopnstock.user.User
import com.yablunin.shopnstock.util.DatabaseHandler
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ShoppingListActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var list: ShoppingList

    private var unit: String = "pc(s)"
    private var expirationDate: String = ""

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)

        val backButton: ImageView = findViewById(R.id.shopping_list_back_button)
        val addItemButton: Button = findViewById(R.id.shopping_list_add_item_button)

        user = intent.getSerializableExtra("user_data", User::class.java)!!

        updateListUIWithUser(user, 0)

        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        addItemButton.setOnClickListener {
            showAddItemPopup()
        }

    }

    fun updateListUIWithUser(user: User, defaultId: Int){
        val list = ShoppingListHandler.getListById(user, intent.getIntExtra("list_id", defaultId))!!
        defaultUpdateListUI(list)
    }

    fun updateListUIWithList(list: ShoppingList){
        defaultUpdateListUI(list)
    }

    @SuppressLint("SetTextI18n")
    private fun defaultUpdateListUI(list: ShoppingList){
        val listName: TextView = findViewById(R.id.shopping_list_name_text)
        val listItemsCountText: TextView = findViewById(R.id.shopping_list_items_count)

        val nothingObj: LinearLayout = findViewById(R.id.shopping_list_empty_list_obj)
        val itemsRcView: RecyclerView = findViewById(R.id.shopping_list_items_rcview)

        listName.text = list.name
        nothingObj.visibility = View.GONE
        itemsRcView.visibility = View.GONE

        if (list.size() > 0){
            listItemsCountText.text = "List ${list.getCompletedItemsCount()} / ${list.size()} completed"
            itemsRcView.visibility = View.VISIBLE
            itemsRcView.layoutManager = LinearLayoutManager(this)
            itemsRcView.adapter = ShoppingListItemsAdapter(list.list, user, this)
        }
        else{
            listItemsCountText.text = "Nothing here"
            nothingObj.visibility = View.VISIBLE
        }
    }

    private fun showAddItemPopup(){
        val addItemPopup = Dialog(this)
        addItemPopup.setContentView(R.layout.add_new_item_popup)
        addItemPopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        addItemPopup.show()

        val closeButton: ImageView = addItemPopup.findViewById(R.id.shopping_list_back_button)
        val addItemButton: Button = addItemPopup.findViewById(R.id.shopping_list_add_item_button)

        val nameInput: EditText = addItemPopup.findViewById(R.id.add_new_item_input_name)
        val quantityInput: EditText = addItemPopup.findViewById(R.id.add_new_item_input_quantity)
        val unitSpinner: Spinner = addItemPopup.findViewById(R.id.add_new_item_spinner_unit)
        val priceInput: EditText = addItemPopup.findViewById(R.id.add_new_item_input_price)
        val expirationDateInput: EditText = addItemPopup.findViewById(R.id.add_new_item_input_exp_date)

        closeButton.setOnClickListener {
            addItemPopup.dismiss()
        }

        addItemButton.setOnClickListener {
            if (nameInput.text.trim().isNotEmpty() && quantityInput.text.trim().isNotEmpty()
                && unit.trim().isNotEmpty() && priceInput.text.trim().isNotEmpty() && expirationDate.trim().isNotEmpty()){
                val name = nameInput.text.toString()
                val quantity = quantityInput.text.toString().toInt()
                val price = priceInput.text.toString().toDouble()

                val itemId = list.size()
                val item = ListItem(itemId, name, quantity, price, unit, expirationDate)
                list.add(item)

                updateListUIWithList(list)

                DatabaseHandler.save(DatabaseHandler.DB_REFERENCE, user)

                addItemPopup.dismiss()
            }
        }

        expirationDateInput.setOnClickListener {
            showSetExpirationDateDialog(expirationDateInput)
        }

        val unitData = arrayOf("pc(s)", "kg", "g", "l")
        val unitSpinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unitData)
        unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        unitSpinner.adapter = unitSpinnerAdapter

        unitSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val selectedValue = parentView.getItemAtPosition(position) as String
                unit = selectedValue
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Вызывается, если ничего не выбрано
            }
        })
    }

    private fun showSetExpirationDateDialog(dateInput: EditText){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = formatDate(selectedDay, selectedMonth + 1, selectedYear)
            expirationDate = selectedDate
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