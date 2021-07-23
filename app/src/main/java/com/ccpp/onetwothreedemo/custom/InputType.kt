package com.ccpp.onetwothreedemo.custom

enum class InputType {
    merchantId,
    redirectURL,
    notificationURL,
    merchantData,
    merchantReference,
    preferredAgent,
    productDesc,
    paymentInfo,
    amount,
    currencyCode,
    paymentExpiry,
    userDefined1,
    userDefined2,
    userDefined3,
    userDefined4,
    userDefined5,
    buyerEmail,
    buyerMobile,
    buyerLanguage,
    buyerOS,
    buyerNotify,
    buyerTitle,
    buyerFirstName,
    buyerLastName;

    var value: String = ""

    val title: String
        get() = when(this){
            merchantId ->        "Merchant ID"
            redirectURL ->       "Redirect URL"
            notificationURL ->   "Notification URL"
            merchantData ->      "Merchant Data"

            merchantReference -> "Merchant Reference"
            preferredAgent ->    "Preferred Agent (BAY, BBL, SCB, etc.)"
            productDesc ->       "Product Description"
            paymentInfo ->       "Payment Info"
            amount ->            "Amount"
            currencyCode ->      "Currency Code (THB, USD, etc.)"
            paymentExpiry ->     "Payment Expiry (yyyy-MM-dd HH:mm:ss)"
            userDefined1 ->      "User Defined 1"
            userDefined2 ->      "User Defined 2"
            userDefined3 ->      "User Defined 3"
            userDefined4 ->      "User Defined 4"
            userDefined5 ->      "User Defined 5"

            buyerEmail ->        "Buyer Email"
            buyerMobile ->       "Buyer Mobile"
            buyerLanguage ->     "Buyer Language (EN, TH, etc.)"
            buyerOS ->           "Buyer OS (0 = iOS / 1 = Android)"
            buyerNotify ->       "Notify Buyer (true / false)"
            buyerTitle ->        "Buyer Title"
            buyerFirstName ->    "Buyer Name"
            buyerLastName ->     "Buyer Surname"
        }

    val hint: String
        get() = when(this){
            merchantId ->        "Enter Merchant ID"
            redirectURL ->       "Enter Redirect URL"
            notificationURL ->   "Enter Notification URL"
            merchantData ->      "value1:value2:value3"

            merchantReference -> "Enter Merchant Reference"
            preferredAgent ->    "Enter Preferred Agent"
            productDesc ->       "Enter Product Description"
            paymentInfo ->       "Enter Payment Info"
            amount ->            "Enter Amount"
            currencyCode ->      "Enter Currency Code"
            paymentExpiry ->     "Enter Payment Expiry"
            userDefined1 ->      "User Defined 1"
            userDefined2 ->      "User Defined 2"
            userDefined3 ->      "User Defined 3"
            userDefined4 ->      "User Defined 4"
            userDefined5 ->      "User Defined 5"

            buyerEmail ->        "Enter Buyer Email"
            buyerMobile ->       "Enter Buyer Mobile"
            buyerLanguage ->     "Enter Buyer Language"
            buyerOS ->           "Enter Buyer OS"
            buyerNotify ->       "Enter Notify Buyer"
            buyerTitle ->        "Enter Buyer Title"
            buyerFirstName ->    "Enter Buyer Name"
            buyerLastName ->     "Enter Buyer Surname"
        }
}