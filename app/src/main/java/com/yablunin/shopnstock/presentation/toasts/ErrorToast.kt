package com.yablunin.shopnstock.presentation.toasts

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.yablunin.shopnstock.R

class ErrorToast(var context: Context, var message: String, var duration: Int, val gravity: Int) {

    fun show(){
        val layout = LayoutInflater.from(context).inflate(R.layout.error_toast_layout, null)
        val toast = Toast(context)
        toast.duration = duration
        toast.view = layout

        val toastText: TextView = layout.findViewById(R.id.error_toast_text)
        toastText.text = message

        toast.setGravity(gravity, 0,0)

        toast.show()
    }
}