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
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.app.App
import com.yablunin.shopnstock.presentation.adapters.ShoppingListAdapter
import com.yablunin.shopnstock.databinding.ActivityHomeBinding
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.util.Initiable
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
    }

    override fun init(){
        (applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        viewModel.userLiveData.observe(this) { _user ->
            user = _user
            updateUI()
        }

        binding.homeCreateTaskButton.setOnClickListener{
            showCreateListDialog()
        }

        runBlocking {
            launch {
                viewModel.loadUser()
            }
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
        dialog.show()
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val drawable = ColorDrawable(Color.TRANSPARENT)
        window?.setBackgroundDrawable(drawable)
        window?.attributes?.windowAnimations = R.style.BottomSheetAnimation
        window?.setGravity(Gravity.BOTTOM)

        val createNewListButton: Button = dialog.findViewById(R.id.create_list_button)
        val createNewListInput: EditText = dialog.findViewById(R.id.create_list_name_input)
        createNewListButton.setOnClickListener {
            createList(dialog, createNewListInput)
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
}