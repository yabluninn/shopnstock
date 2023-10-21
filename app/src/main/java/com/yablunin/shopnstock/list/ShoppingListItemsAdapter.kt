package com.yablunin.shopnstock.list

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.ShoppingListActivity
import com.yablunin.shopnstock.user.User
import com.yablunin.shopnstock.util.DatabaseHandler

class ShoppingListItemsAdapter(val items: MutableList<ListItem>, val user: User, val listActivity: ShoppingListActivity): RecyclerView.Adapter<ShoppingListItemsAdapter.Holder>() {

    class Holder(view: View, val listActivity: ShoppingListActivity): RecyclerView.ViewHolder(view) {
        lateinit var user: User
        val layout: LinearLayout = view.findViewById(R.id.list_item_holder)
        val checkbox: CheckBox = view.findViewById(R.id.list_item_checkbox)
        val itemName: TextView = view.findViewById(R.id.list_item_name)
        fun bind(item: ListItem){
            checkbox.isChecked = item.isCompleted
            checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                item.isCompleted = isChecked
                updateItemHolderUI(item)
                listActivity.updateListUIWithUser(user, 0)

                DatabaseHandler.save(DatabaseHandler.DB_REFERENCE, user)
            }

            itemName.text = item.name

            updateItemHolderUI(item)
        }

        private fun updateItemHolderUI(item: ListItem){
            if(item.isCompleted){
                layout.setBackgroundResource(R.drawable.list_item_holder_background_checked)
                itemName.setTextColor(Color.parseColor("#e6994e"))
                itemName.paintFlags = itemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            else{
                layout.setBackgroundResource(R.drawable.list_item_holder_background)
                itemName.setTextColor(Color.parseColor("#554538"))
                itemName.paintFlags = itemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_holder, parent, false)
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