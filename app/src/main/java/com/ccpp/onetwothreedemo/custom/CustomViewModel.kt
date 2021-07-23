package com.ccpp.onetwothreedemo.custom

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ccpp.onetwothreedemo.constant.TestConstants
import com.ccpp.onetwothreedemo.util.liveEvent
import com.ccpp.onetwothreesdk.callback.APIResponseCallback
import com.ccpp.onetwothreesdk.core.OneTwoThreeSDKService
import com.ccpp.onetwothreesdk.model.Buyer
import com.ccpp.onetwothreesdk.model.Merchant
import com.ccpp.onetwothreesdk.model.Transaction
import com.ccpp.onetwothreesdk.model.response.StartDeeplinkResponse

class CustomViewModel : ViewModel() {

    sealed class Event {
        object OnClickConfirm : Event()
        data class OnConfirmSuccess(val res: StartDeeplinkResponse) : Event()
        data class OnConfirmFailed(val error: Throwable?) : Event()
    }

    val event = liveEvent<Event>()
    val isInProcess = MutableLiveData(false)
    val isProduction = MutableLiveData(false)

    fun switchProduction() {
        isProduction.value = !(isProduction.value!!)

        OneTwoThreeSDKService.initialize(
            isProduction = isProduction.value!!,
            TestConstants.checksumKey,
            TestConstants.publicKey,
            TestConstants.privateKey,
            TestConstants.passphrase,
            TestConstants.bksPassphrase
        )
    }

    fun confirm() {
        event.value = Event.OnClickConfirm
    }

    fun callService(merchant: Merchant, transaction: Transaction, buyer: Buyer) {
        isInProcess.value = true
        OneTwoThreeSDKService.startDeeplink(merchant, transaction, buyer, object :
            APIResponseCallback<StartDeeplinkResponse> {
            override fun onResponse(response: StartDeeplinkResponse) {
                isInProcess.value = false
                Log.d("API", "onResponse: $response")
                event.value = Event.OnConfirmSuccess(response)
            }

            override fun onFailure(error: Throwable?) {
                isInProcess.value = false
                Log.e("API", "onFailure: ${error?.message}")
                event.value = Event.OnConfirmFailed(error)
            }
        })
    }
}