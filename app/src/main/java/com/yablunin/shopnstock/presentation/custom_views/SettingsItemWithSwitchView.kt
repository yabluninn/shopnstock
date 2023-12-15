package com.yablunin.shopnstock.presentation.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.yablunin.shopnstock.R

class SettingsItemWithSwitchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {
    private val titleTextView: TextView
    private val switchButton: SwitchCompat
    private var onCheckedListener: ((isChecked: Boolean) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.settings_item_view_with_switch, this, true)
        titleTextView = findViewById(R.id.settings_switch_view_title)
        switchButton = findViewById(R.id.settings_switch_button)

        switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            onCheckedListener?.invoke(isChecked)
        }
    }

    fun title(title: String){
        titleTextView.text = title
    }

    fun setOnSwitchCheckedListener(listener: (isChecked: Boolean) -> Unit){
        onCheckedListener = listener
    }

    fun isChecked(): Boolean{
        return switchButton.isChecked
    }

    fun setCheckedState(isChecked: Boolean){
        switchButton.isChecked = isChecked
    }
}