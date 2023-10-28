package com.yablunin.shopnstock.presentation.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
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
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.domain.list.ListItem
import com.yablunin.shopnstock.domain.list.ShoppingList
import com.yablunin.shopnstock.domain.list.ShoppingListHandler
import com.yablunin.shopnstock.presentation.adapters.ShoppingListItemsAdapter
import com.yablunin.shopnstock.domain.user.User
import com.yablunin.shopnstock.data.DatabaseHandler
import com.yablunin.shopnstock.databinding.ActivityShoppingListBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ShoppingListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingListBinding
    private lateinit var user: User
    private lateinit var list: ShoppingList

    private var unit: String = "pc(s)"
    private var expirationDate: String = ""

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra("user_data", User::class.java)!!

        updateListUIWithUser(user, 0)

        binding.shoppingListBackButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.shoppingListAddItemButton.setOnClickListener {
            showAddItemPopup()
        }

        binding.shoppingListMenuButton.setOnClickListener {
            showListMenu()
        }

    }

    fun updateListUIWithUser(user: User, defaultId: Int){
        list = ShoppingListHandler.getListById(user, intent.getIntExtra("list_id", defaultId))!!
        defaultUpdateListUI(list)
    }

    fun updateListUIWithList(){
        defaultUpdateListUI(list)
    }

    @SuppressLint("SetTextI18n")
    private fun defaultUpdateListUI(list: ShoppingList){
        binding.shoppingListNameText.text = list.name
        binding.shoppingListEmptyListObj.visibility = View.GONE
        binding.shoppingListItemsRcview.visibility = View.GONE

        if (list.size() > 0){
            binding.shoppingListItemsCount.text = "List ${list.getCompletedItemsCount()} / ${list.size()} completed"
            binding.shoppingListItemsRcview.visibility = View.VISIBLE
            binding.shoppingListItemsRcview.layoutManager = LinearLayoutManager(this)
            binding.shoppingListItemsRcview.adapter = ShoppingListItemsAdapter(list.list, user, this)
        }
        else{
            binding.shoppingListItemsCount.text = "Nothing here"
            binding.shoppingListEmptyListObj.visibility = View.VISIBLE
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
                list.addItem(item)

                updateListUIWithList()

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

    private fun showListMenu(){
        val menuPopup = Dialog(this)
        menuPopup.setContentView(R.layout.list_menu_popup)
        menuPopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val layoutParams = menuPopup.window?.attributes
        layoutParams?.gravity = Gravity.END or Gravity.TOP
        menuPopup.window?.attributes = layoutParams

        menuPopup.show()

        val deleteOption: LinearLayout = menuPopup.findViewById(R.id.list_menu_delete_option)
        deleteOption.setOnClickListener {
            ShoppingListHandler.removeList(list, user)
            DatabaseHandler.save(DatabaseHandler.DB_REFERENCE, user)
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    fun showDeleteItemPopup(item: ListItem){
        val deletePopup = Dialog(this)
        deletePopup.setContentView(R.layout.delete_item_warning_popup)
        deletePopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        deletePopup.show()

        val yesButton: TextView = deletePopup.findViewById(R.id.delete_item_yes)
        val noButton: TextView = deletePopup.findViewById(R.id.delete_item_no)

        val deleteHeader: TextView = deletePopup.findViewById(R.id.delete_item_header)

        yesButton.setOnClickListener {
            list.removeItem(item)
            DatabaseHandler.save(DatabaseHandler.DB_REFERENCE, user)
            updateListUIWithList()
            deletePopup.dismiss()
        }
        noButton.setOnClickListener {
            deletePopup.dismiss()
        }
        deleteHeader.text = "Delete ${item.name}?"
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