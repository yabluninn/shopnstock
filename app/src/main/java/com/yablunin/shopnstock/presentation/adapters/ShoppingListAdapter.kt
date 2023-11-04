package com.yablunin.shopnstock.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.domain.models.ShoppingList
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.presentation.activities.ShoppingListActivity
import com.yablunin.shopnstock.domain.repositories.ShoppingListRepository
import com.yablunin.shopnstock.domain.usecases.list.AddItemUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetCompletedItemsCountUseCase
import com.yablunin.shopnstock.domain.usecases.list.GetSizeUseCase
import com.yablunin.shopnstock.domain.usecases.list.RemoveItemUseCase

class ShoppingListAdapter(val context: Context, val shoppingLists: MutableList<com.yablunin.shopnstock.domain.models.ShoppingList>, val user: com.yablunin.shopnstock.domain.models.User):
    RecyclerView.Adapter<ShoppingListAdapter.Holder>() {

    class Holder(view: View): RecyclerView.ViewHolder(view) {

        val listName: TextView = view.findViewById(R.id.shopping_list_item_name)
        val listItemsCountText: TextView = view.findViewById(R.id.shopping_list_item_count)
        val holderLayout: ConstraintLayout = view.findViewById(R.id.shopping_list_holder)

        private val addItemUseCase = AddItemUseCase(ShoppingListRepository())
        private val removeItemUseCase = RemoveItemUseCase(ShoppingListRepository())
        private val getSizeUseCase = GetSizeUseCase(ShoppingListRepository())
        private val getCompletedItemsCountUseCase = GetCompletedItemsCountUseCase(
            ShoppingListRepository()
        )

        @SuppressLint("SetTextI18n")
        fun bind(list: com.yablunin.shopnstock.domain.models.ShoppingList){

            listName.text = list.name
            val size = getSizeUseCase.execute(list)
            val completedItemsCount = getCompletedItemsCountUseCase.execute(list)

            if (size > 0){
                listItemsCountText.text = "List $completedItemsCount / $size completed"
            }
            else{
                listItemsCountText.text = "Nothing here"
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_list_holder_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return shoppingLists.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(shoppingLists[position])
        holder.holderLayout.setOnClickListener {
            val intent = Intent(context, ShoppingListActivity::class.java);
            intent.putExtra("list_id", shoppingLists[position].id)
            intent.putExtra("user_data", user)

            context.startActivity(intent);
        }
        animateItem(holder.itemView, position)
    }

    private fun animateItem(view: View, position: Int){
        val slideIn: Animation = AnimationUtils.loadAnimation(context, R.anim.bottom_sheet_slide_in)
        view.startAnimation(slideIn)
    }
}