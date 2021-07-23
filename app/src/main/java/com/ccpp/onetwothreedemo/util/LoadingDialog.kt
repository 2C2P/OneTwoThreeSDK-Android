package com.ccpp.onetwothreedemo.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.ccpp.onetwothreedemo.R

class LoadingDialog(context: Context) : Dialog(context) {
    init {
        val loadingView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        setContentView(loadingView)
    }
}