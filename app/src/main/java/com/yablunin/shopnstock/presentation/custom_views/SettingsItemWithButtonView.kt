package com.yablunin.shopnstock.presentation.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.yablunin.shopnstock.R

class SettingsItemWithButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {
    private val titleTextView: TextView
    private val button: ImageView
    private var onClickListener: (() -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.settings_item_view_with_button, this, true)
        titleTextView = findViewById(R.id.settings_view_title)
        button = findViewById(R.id.settings_view_button)

        button.setOnClickListener {
            onClickListener?.invoke()
        }
    }

    fun title(title: String){
        titleTextView.text = title
    }

    fun setOnButtonClickListener(listener: () -> Unit){
        onClickListener = listener
    }
}