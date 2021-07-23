package com.ccpp.onetwothreedemo.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ccpp.onetwothreedemo.R
import com.ccpp.onetwothreedemo.constant.TestConstants
import com.ccpp.onetwothreedemo.model.Product
import com.ccpp.onetwothreesdk.core.OneTwoThreeSDKService

class ListViewModel : ViewModel() {

    fun getItems(): List<Product> {
        return listOf(
            Product(
                name = "Omega",
                desc = "Founded in 1848 in a small Swiss village under the name Louis Brandt & Fil.",
                amount = "1.0",
                image = R.drawable.watch2
            ),
            Product(
                name = "Laurent Ferrier",
                desc = "The designs are precise, simple, and unfussy.",
                amount = "1.5",
                image = R.drawable.watch3
            ),
            Product(
                name = "A. Lange and Söhne",
                desc = "German luxury watch founded in 1845.",
                amount = "10.0",
                image = R.drawable.watch1
            )
        )
    }
}