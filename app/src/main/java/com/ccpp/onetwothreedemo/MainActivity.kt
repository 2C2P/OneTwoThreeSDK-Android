package com.ccpp.onetwothreedemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ccpp.onetwothreedemo.list.ListFragment
import com.ccpp.onetwothreesdk.callback.APIResponseCallback
import com.ccpp.onetwothreesdk.core.OneTwoThreeSDKService
import com.ccpp.onetwothreesdk.model.Buyer
import com.ccpp.onetwothreesdk.model.Merchant
import com.ccpp.onetwothreesdk.model.MerchantData
import com.ccpp.onetwothreesdk.model.Transaction
import com.ccpp.onetwothreesdk.model.response.StartDeeplinkResponse


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, ListFragment.newInstance())
            commit()
        }

    }

}