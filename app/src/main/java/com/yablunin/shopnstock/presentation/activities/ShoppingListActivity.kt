package com.yablunin.shopnstock.presentation.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Bitmap
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.app.App
import com.yablunin.shopnstock.presentation.adapters.ShoppingListItemsAdapter
import com.yablunin.shopnstock.databinding.ActivityShoppingListBinding
import com.yablunin.shopnstock.domain.constants.ListConstants
import com.yablunin.shopnstock.domain.models.ListItem
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.util.Formatter
import com.yablunin.shopnstock.domain.util.Initiable
import com.yablunin.shopnstock.presentation.viewmodels.ShoppingListViewModel
import com.yablunin.shopnstock.presentation.viewmodels.ShoppingListViewModelFactory
import java.util.Calendar
import javax.inject.Inject


class ShoppingListActivity : AppCompatActivity(), Initiable {

    private lateinit var binding: ActivityShoppingListBinding
    private lateinit var user: User
    private lateinit var list: ShoppingList
    private lateinit var viewModel: ShoppingListViewModel

    @Inject
    lateinit var viewModelFactory: ShoppingListViewModelFactory

    private var unit: String = "pc(s)"
    private var expirationDate: String = ""

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun init() {
        (applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ShoppingListViewModel::class.java)

        viewModel.listData.observe(this){ _list ->
            if (_list != null) {
                list = _list
                updateListUI()
            }
        }

        user = intent.getSerializableExtra("user_data") as User
        viewModel.getListById(user, 0, intent)

        binding.shoppingListBackButton.setOnClickListener {
            onClickReturnBackListener()
        }
        binding.shoppingListAddItemButton.setOnClickListener {
            onClickShowAddItemPopup()
        }
        binding.shoppingListMenuButton.setOnClickListener {
            onClickShowListMenu()
        }
    }


    @SuppressLint("SetTextI18n")
    fun updateListUI(){
        binding.shoppingListNameText.text = list.name
        binding.shoppingListEmptyListObj.visibility = View.GONE
        binding.shoppingListItemsRcview.visibility = View.GONE

        viewModel.getListSize()
        val listSize = viewModel.listSizeData.value!!

        viewModel.getCompletedItemsCount()
        val completedItemsCount = viewModel.completedItemsCountData.value!!
        viewModel.getTotalPrice(list)
        val totalPrice = viewModel.totalPriceLiveData.value ?: 0.0

        binding.shoppingListPriceBudgetText.text = "Worth: $totalPrice USD (max: ${list.budget})"

        setListAdapterContent(listSize, completedItemsCount)
    }

    private fun setOnClickRenameList(renamePopup: Dialog){
        val renameButton: TextView = renamePopup.findViewById(R.id.rename_list_button)
        val newNameInput: EditText = renamePopup.findViewById(R.id.rename_list_new_name_input)

        renameButton.setOnClickListener {
            val newName = newNameInput.text.toString()
            if (newName.trim().isNotEmpty()){
                viewModel.renameList(newName, user)
                binding.shoppingListNameText.text = newName
                renamePopup.dismiss()
            }
        }

    }
    private fun setOnClickCancelRenamingList(renamePopup: Dialog){
        val cancelButton: TextView = renamePopup.findViewById(R.id.rename_list_cancel_button)
        cancelButton.setOnClickListener {
            renamePopup.dismiss()
        }
    }
    private fun setOnClickDeleteItem(deletePopup: Dialog, item: ListItem){
        val yesButton: TextView = deletePopup.findViewById(R.id.delete_item_yes)
        yesButton.setOnClickListener {
            onClickDeleteItem(deletePopup, item)
        }
    }
    private fun setOnClickCancelDeletingItem(deletePopup: Dialog){
        val noButton: TextView = deletePopup.findViewById(R.id.delete_item_no)
        noButton.setOnClickListener {
            deletePopup.dismiss()
        }
    }

    private fun onClickReturnBackListener(){
        viewModel.showHomeActivity(this)
    }
    private fun onClickShowAddItemPopup(){
        showAddItemPopup()
    }
    private fun onClickShowListMenu(){
        showListMenu()
    }

    private fun onClickAddItemListener(addItemPopup: Dialog){
        val nameInput: EditText = addItemPopup.findViewById(R.id.add_new_item_input_name)
        val quantityInput: EditText = addItemPopup.findViewById(R.id.add_new_item_input_quantity)
        val priceInput: EditText = addItemPopup.findViewById(R.id.add_new_item_input_price)
        if (nameInput.text.trim().isNotEmpty() && quantityInput.text.trim().isNotEmpty()
            && unit.trim().isNotEmpty() && priceInput.text.trim().isNotEmpty() && expirationDate.trim().isNotEmpty()){
            val name = nameInput.text.toString()
            val quantity = quantityInput.text.toString().toInt()
            val price = priceInput.text.toString().toDouble()

            viewModel.addItem(
                name,
                quantity,
                price,
                unit,
                expirationDate,
                user,
                this
            )

            updateListUI()
            addItemPopup.dismiss()
        }
    }
    private fun onClickDeleteItem(deletePopup: Dialog, item: ListItem){
        viewModel.removeItem(item, user)
        viewModel.saveUser(user)
        updateListUI()
        deletePopup.dismiss()
    }

    @SuppressLint("SetTextI18n")
    private fun setListAdapterContent(listSize: Int, completedItemsCount: Int){
        if (listSize > 0){
            binding.shoppingListItemsCount.text = "List $completedItemsCount / $listSize purchased"
            binding.shoppingListItemsRcview.visibility = View.VISIBLE
            binding.shoppingListItemsRcview.layoutManager = LinearLayoutManager(this)
            binding.shoppingListItemsRcview.adapter = ShoppingListItemsAdapter(list.list, user, this)
        }
        else{
            binding.shoppingListItemsCount.text = "Nothing here"
            binding.shoppingListEmptyListObj.visibility = View.VISIBLE
        }
    }
    private fun setUnitSpinnerContent(addItemPopup: Dialog){
        val unitSpinner: Spinner = addItemPopup.findViewById(R.id.add_new_item_spinner_unit)
        val unitData = arrayOf("pc(s)", "kg", "g", "l")
        val unitSpinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unitData)
        unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        unitSpinner.adapter = unitSpinnerAdapter

        unitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

            }
        }
    }

    private fun showAddItemPopup(){
        val addItemPopup = Dialog(this)
        addItemPopup.setContentView(R.layout.add_new_item_popup)
        addItemPopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        addItemPopup.show()

        val closeButton: ImageView = addItemPopup.findViewById(R.id.shopping_list_back_button)
        closeButton.setOnClickListener {
            addItemPopup.dismiss()
        }
        val addItemButton: Button = addItemPopup.findViewById(R.id.shopping_list_add_item_button)
        addItemButton.setOnClickListener {
            onClickAddItemListener(addItemPopup)
        }
        val expirationDateInput: EditText = addItemPopup.findViewById(R.id.add_new_item_input_exp_date)
        expirationDateInput.setOnClickListener {
            showSetExpirationDatePopup(expirationDateInput)
        }

        setUnitSpinnerContent(addItemPopup)
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
            viewModel.removeList(user, this)
        }
        val renameOption: LinearLayout = menuPopup.findViewById(R.id.list_menu_rename_option)
        renameOption.setOnClickListener {
            showRenameListPopup(menuPopup)
        }
        val changeBudget: LinearLayout = menuPopup.findViewById(R.id.list_menu_change_budget_option)
        changeBudget.setOnClickListener {
            showChangeBudgetPopup(menuPopup)
        }
        val copyOption: LinearLayout = menuPopup.findViewById(R.id.list_menu_copy_option)
        copyOption.setOnClickListener {
            showCopyListPopup(menuPopup)
        }
        val shareOption: LinearLayout = menuPopup.findViewById(R.id.list_menu_share_option)
        shareOption.setOnClickListener {
            showShareListPopup(menuPopup)
        }
        val deletePurchasedOption: LinearLayout = menuPopup.findViewById(R.id.list_menu_delete_purchased_option)
        deletePurchasedOption.setOnClickListener {
            viewModel.deletePurchasedItems(user, this)
            menuPopup.dismiss()
            updateListUI()
        }
        val uncheckAllOption: LinearLayout = menuPopup.findViewById(R.id.list_menu_uncheck_all_option)
        uncheckAllOption.setOnClickListener {
            viewModel.uncheckAllItems(user, this)
            menuPopup.dismiss()
            updateListUI()
        }
    }
    @SuppressLint("SetTextI18n")
    fun showDeleteItemPopup(item: ListItem){
        val deletePopup = Dialog(this)
        deletePopup.setContentView(R.layout.delete_item_warning_popup)
        deletePopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        deletePopup.show()

        setOnClickDeleteItem(deletePopup, item)
        setOnClickCancelDeletingItem(deletePopup)

        val deleteHeader: TextView = deletePopup.findViewById(R.id.delete_item_header)
        deleteHeader.text = "Delete ${item.name}?"
    }
    private fun showSetExpirationDatePopup(dateInput: EditText){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = Formatter.formatDate(selectedDay, selectedMonth + 1, selectedYear)
            expirationDate = selectedDate
            dateInput.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showRenameListPopup(menuPopup: Dialog){
        val renamePopup = Dialog(this)
        renamePopup.setContentView(R.layout.rename_list_popup)
        renamePopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        menuPopup.dismiss()
        renamePopup.show()

        val currentListNameText: TextView = renamePopup.findViewById(R.id.rename_list_current_name_text)
        currentListNameText.text = list.name

        setOnClickRenameList(renamePopup)
        setOnClickCancelRenamingList(renamePopup)
    }

    private fun showCopyListPopup(menuPopup: Dialog){
        val copyPopup = Dialog(this)
        copyPopup.setContentView(R.layout.copy_list_popup)
        copyPopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        menuPopup.dismiss()
        copyPopup.show()

        val copyWholeListButton: LinearLayout = copyPopup.findViewById(R.id.copy_list_whole_button)
        copyWholeListButton.setOnClickListener{
            viewModel.copyList(ListConstants.COPY_WHOLE_LIST, list, user, this)
        }
        val copyUnpurchasedItemsListButton: LinearLayout = copyPopup.findViewById(R.id.copy_list_unpurchased_button)
        copyUnpurchasedItemsListButton.setOnClickListener{
            viewModel.copyList(ListConstants.COPY_UNPURCHASED_ITEMS_LIST, list, user, this)
        }
        val copyPurchasedItemsListButton: LinearLayout = copyPopup.findViewById(R.id.copy_list_purchased_button)
        copyPurchasedItemsListButton.setOnClickListener{
            viewModel.copyList(ListConstants.COPY_PURCHASED_ITEMS_LIST, list, user, this)
        }
        val cancelButton: TextView = copyPopup.findViewById(R.id.copy_list_cancel_button)
        cancelButton.setOnClickListener {
            copyPopup.dismiss()
        }
    }

    private fun showShareListPopup(menuPopup: Dialog){
        val sharePopup = Dialog(this)
        sharePopup.setContentView(R.layout.share_list_popup)
        sharePopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        menuPopup.dismiss()
        sharePopup.show()

        val shareListToClipboardButton: ImageView = sharePopup.findViewById(R.id.share_list_clipboard_button)
        shareListToClipboardButton.setOnClickListener {
            viewModel.shareList(ListConstants.SHARE_CLIPBOARD_OPTION, list, user, this)
            sharePopup.dismiss()
        }
        val shareListWithQRCodeButton: ImageView = sharePopup.findViewById(R.id.share_list_qrcode_button)
        shareListWithQRCodeButton.setOnClickListener {
            viewModel.shareList(ListConstants.SHARE_QRCODE_OPTION, list, user, this)
            sharePopup.dismiss()
            showQrCodePopup()
        }


        val cancelButton: TextView = sharePopup.findViewById(R.id.share_list_cancel_button)
        cancelButton.setOnClickListener {
            sharePopup.dismiss()
        }
    }

    private fun showQrCodePopup(){
        val qrCodePopup = Dialog(this)
        qrCodePopup.setContentView(R.layout.share_qr_code_popup)
        qrCodePopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        qrCodePopup.show()

        val qrCodeImage: ImageView = qrCodePopup.findViewById(R.id.share_qr_code_image)
        qrCodeImage.setImageBitmap(viewModel.qrCodeBitmap)

        val cancelButton: TextView = qrCodePopup.findViewById(R.id.share_qr_code_cancel_button)
        cancelButton.setOnClickListener {
            qrCodePopup.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showChangeBudgetPopup(menuPopup: Dialog){
        val popup = Dialog(this)
        popup.setContentView(R.layout.change_budget_popup)
        popup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        menuPopup.dismiss()
        popup.show()

        val newBudgetInput: EditText = popup.findViewById(R.id.change_budget_input)
        val changeButton: TextView = popup.findViewById(R.id.change_budget_button)
        changeButton.setOnClickListener {
            if (newBudgetInput.text.trim().isNotEmpty()){
                val newBudget = newBudgetInput.text.toString().toDouble()
                viewModel.changeBudget(newBudget, user, this)
                val totalPrice = viewModel.totalPriceLiveData.value ?: 0.0
                binding.shoppingListPriceBudgetText.text = "Worth: $totalPrice USD (max: ${list.budget})"
                popup.dismiss()
            }
        }

        val cancelButton: TextView = popup.findViewById(R.id.change_budget_cancel_button)
        cancelButton.setOnClickListener {
            popup.dismiss()
        }
    }
}