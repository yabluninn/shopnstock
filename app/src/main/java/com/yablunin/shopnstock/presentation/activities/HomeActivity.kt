package com.yablunin.shopnstock.presentation.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.app.App
import com.yablunin.shopnstock.presentation.adapters.ShoppingListAdapter
import com.yablunin.shopnstock.databinding.ActivityHomeBinding
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.util.Initiable
import com.yablunin.shopnstock.presentation.custom_views.SettingsItemWithButtonView
import com.yablunin.shopnstock.presentation.custom_views.SettingsItemWithSwitchView
import com.yablunin.shopnstock.presentation.toasts.ErrorToast
import com.yablunin.shopnstock.presentation.toasts.SuccessfulToast
import com.yablunin.shopnstock.presentation.viewmodels.HomeViewModel
import com.yablunin.shopnstock.presentation.viewmodels.HomeViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), Initiable {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var user: User

//    private val viewModel by viewModel<HomeViewModel>()
    @Inject
    lateinit var viewModelFactory: HomeViewModelFactory

    private lateinit var viewModel: HomeViewModel

    private lateinit var createListDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val gson = Gson()
                val receivedShoppingList = gson.fromJson(result.contents, ShoppingList::class.java)
                viewModel.addList(receivedShoppingList)
                viewModel.saveUser()

                updateUI()

                createListDialog.dismiss()

                val successfulToast = SuccessfulToast(
                    this,
                    this.getString(R.string.successful_create_list),
                    Toast.LENGTH_LONG,
                    Gravity.TOP
                )
                successfulToast.show()
            } else {
                createListDialog.dismiss()

                val errorToast = ErrorToast(
                    this,
                    this.getString(R.string.error_scanning_list),
                    Toast.LENGTH_LONG,
                    Gravity.TOP
                )
                errorToast.show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun init(){
        (applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        viewModel.userLiveData.observe(this) { _user ->
            user = _user
            updateUI()
        }

        runBlocking {
            launch {
                viewModel.loadUser()
            }
        }

        binding.homeCreateTaskButton.setOnClickListener{
            showCreateListDialog()
        }
        binding.homeMenuButton.setOnClickListener {
            showUserMenuPopup()
        }
    }

    private fun updateUI(){
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

        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val drawable = ColorDrawable(Color.TRANSPARENT)
        window?.setBackgroundDrawable(drawable)
        window?.attributes?.windowAnimations = R.style.BottomSheetAnimation
        window?.setGravity(Gravity.BOTTOM)

        dialog.show()

        createListDialog = dialog

        val createNewListButton: Button = dialog.findViewById(R.id.create_list_button)
        val createNewListInput: EditText = dialog.findViewById(R.id.create_list_name_input)
        createNewListButton.setOnClickListener {
            createList(dialog, createNewListInput)
        }

        val scanQrCodeButton: LinearLayout = dialog.findViewById(R.id.scan_list_button)
        scanQrCodeButton.setOnClickListener {
            initQRScanner()
        }
    }

    private fun createList(dialog: Dialog, createNewListInput: EditText){
        dialog.dismiss()
        if (!createNewListInput.text.trim().isEmpty()){
            val listName: String = createNewListInput.text.trim().toString()
            viewModel.generateListId()
            val listId = viewModel.listIdLiveData.value!!
            val list = ShoppingList(
                listId,
                listName
            )

            val nothingBackground: LinearLayout = binding.homeNothingObj
            val rcView: RecyclerView = binding.homeListsRcView

            viewModel.addList(list)
            viewModel.saveUser()

            rcView.visibility = View.VISIBLE
            rcView.layoutManager = LinearLayoutManager(this)
            val adapter = ShoppingListAdapter(this, user.shoppingLists, user)
            rcView.adapter = adapter

            nothingBackground.visibility = View.GONE

            val successfulToast = SuccessfulToast(
                this,
                this.getString(R.string.successful_create_list),
                Toast.LENGTH_LONG,
                Gravity.TOP
            )
            successfulToast.show()
        }
    }

    private fun initQRScanner(){
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt(getString(R.string.scan_qrcode_to_add_new_list))
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    private fun showUserMenuPopup(){
        val menuPopup = Dialog(this)
        menuPopup.setContentView(R.layout.user_menu_popup)
        menuPopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val layoutParams = menuPopup.window?.attributes
        layoutParams?.gravity = Gravity.END or Gravity.TOP
        menuPopup.window?.attributes = layoutParams

        menuPopup.show()

        val settingsOption: LinearLayout = menuPopup.findViewById(R.id.user_menu_settings_option)
        settingsOption.setOnClickListener {
            menuPopup.dismiss()
            showSettingsBottomDialog()
        }
    }

    private fun showSettingsBottomDialog(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_dialog_settings)

        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val drawable = ColorDrawable(Color.TRANSPARENT)
        window?.setBackgroundDrawable(drawable)
        window?.attributes?.windowAnimations = R.style.BottomSheetAnimation
        window?.setGravity(Gravity.BOTTOM)

        dialog.show()

        val changeUsernameSettingsItem: SettingsItemWithButtonView = dialog.findViewById(R.id.settings_change_username_view)
        changeUsernameSettingsItem.title("Change username")
        changeUsernameSettingsItem.setOnButtonClickListener {
            dialog.dismiss()
            showChangeUsernamePopup()
        }

        val changePasswordSettingItem: SettingsItemWithButtonView = dialog.findViewById(R.id.settings_change_password_view)
        changePasswordSettingItem.title("Change password")
        changePasswordSettingItem.setOnButtonClickListener {
            // TODO Show change password popup
        }

        val enableNotificationsSettingsItem: SettingsItemWithSwitchView = dialog.findViewById(R.id.settings_enable_notifications_switch)
        enableNotificationsSettingsItem.title("Enable push notifications")
        enableNotificationsSettingsItem.setOnSwitchCheckedListener { isChecked ->
            if (isChecked){
                // TODO Enable push notifications
            }
            else{
                // TODO Disable push notifications
            }
        }

        val cancelButton: ImageView = dialog.findViewById(R.id.settings_cancel_button)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun showChangeUsernamePopup(){
        val changeUsernamePopup = Dialog(this)
        changeUsernamePopup.setContentView(R.layout.change_username_popup)
        changeUsernamePopup.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        changeUsernamePopup.show()

        val currentUsernameTextView: TextView = changeUsernamePopup.findViewById(R.id.change_username_textview)
        currentUsernameTextView.text = user.username

        val newUsernameInput: EditText = changeUsernamePopup.findViewById(R.id.change_username_input)
        val changeButton: TextView = changeUsernamePopup.findViewById(R.id.change_username_button)
        changeButton.setOnClickListener {
            val newName = newUsernameInput.text.toString()
            if (newName.trim().isNotEmpty()){
                viewModel.changeUsername(newName, user)
                viewModel.saveUser()
                changeUsernamePopup.dismiss()
                binding.homeUserUsername.text = user.username
            }
        }

        val cancelButton: TextView = changeUsernamePopup.findViewById(R.id.change_username_cancel_button)
        cancelButton.setOnClickListener {
            changeUsernamePopup.dismiss()
        }
    }
}