package com.yablunin.shopnstock.presentation.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.data.repositories.FirebaseUserRepository
import com.yablunin.shopnstock.domain.models.ListItem
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.presentation.activities.ShoppingListActivity
import com.yablunin.shopnstock.domain.usecases.user.SaveUserUseCase

class ShoppingListItemsAdapter(
    private val items: MutableList<ListItem>,
    private val user: User,
    private val listActivity: ShoppingListActivity
): RecyclerView.Adapter<ShoppingListItemsAdapter.Holder>() {

    class Holder(view: View, private val listActivity: ShoppingListActivity): RecyclerView.ViewHolder(view) {
        lateinit var user: User
        private val layout: LinearLayout = view.findViewById(R.id.list_item_holder)
        private val checkbox: CheckBox = view.findViewById(R.id.list_item_checkbox)
        private val itemName: TextView = view.findViewById(R.id.list_item_name)
        private val itemAmount: TextView = view.findViewById(R.id.list_item_amount)
        private val deleteButton: ImageView = view.findViewById(R.id.list_item_delete)
        @SuppressLint("SetTextI18n")
        fun bind(item: ListItem){
            checkbox.isChecked = item.purchased
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                item.purchased = isChecked
                updateHolder(item)
                listActivity.updateListUI()

                val saveUserUseCase = SaveUserUseCase(FirebaseUserRepository())
                saveUserUseCase.execute(user)
            }

            itemName.text = item.name
            itemAmount.text = "${item.quantity} ${item.unit}"

            updateHolder(item)

            deleteButton.setOnClickListener {
                listActivity.showDeleteItemPopup(item)
            }
        }

        private fun updateHolder(item: ListItem){
            if(item.purchased){
                layout.setBackgroundResource(R.drawable.list_item_holder_background_checked)
                itemName.setTextColor(Color.parseColor("#e6994e"))
                itemName.paintFlags = itemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                itemAmount.setTextColor(Color.parseColor("#e6994e"))
            } else{
                layout.setBackgroundResource(R.drawable.list_item_holder_background)
                itemName.setTextColor(Color.parseColor("#554538"))
                itemName.paintFlags = itemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                itemAmount.setTextColor(Color.parseColor("#A5A5A5"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return Holder(view, listActivity)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
        holder.user = this.user
    }
}