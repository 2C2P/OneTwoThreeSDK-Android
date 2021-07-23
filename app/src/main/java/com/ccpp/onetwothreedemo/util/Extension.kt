package com.ccpp.onetwothreedemo.util

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

fun Fragment.showErrorDialog(title: String, message: String, button: String = "OK", callback: (() -> Unit)? = null) {
    val builder = AlertDialog.Builder(requireContext())
        .setCancelable(false)
        .setTitle(title)
        .setMessage(message)
        .setCancelable(false)
        .setNegativeButton(button) { dialog, which ->
            dialog.dismiss()
            callback?.invoke()
        }
    val alert = builder.create()
    alert.show()
}