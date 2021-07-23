package com.ccpp.onetwothreedemo.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import java.text.DecimalFormat
import java.text.NumberFormat

@Parcelize
data class Product (
    var name: String,
    var desc: String,
    var amount: String,
    @DrawableRes var image: Int,
): Parcelable {

    val formattedPrice: String
    get() {
        val price = amount.toDoubleOrNull() ?: 0.00
        val formatter: NumberFormat = DecimalFormat("###,###,###.00")
        return formatter.format(price)
    }
}