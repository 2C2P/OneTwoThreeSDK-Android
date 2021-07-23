package com.ccpp.onetwothreedemo

import android.app.Application
import com.ccpp.onetwothreedemo.constant.TestConstants
import com.ccpp.onetwothreesdk.core.OneTwoThreeSDKService

class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /**
         * Initialize of service environment and necessary key.
         */
        OneTwoThreeSDKService.initialize(
            isProduction = false,
            checkSumKey = TestConstants.checksumKey,
            publicKey = TestConstants.publicKey,
            privateKey = TestConstants.privateKey,
            passphrase = TestConstants.passphrase,
            bksPassphrase = TestConstants.bksPassphrase,
        )
    }
}