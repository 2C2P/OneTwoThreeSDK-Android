# OneTwoThreeSDK for Android

![Minimum SDK Version](https://img.shields.io/badge/minSdkVersion-23-brightgreen)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.2c2p/onetwothreesdk/badge.svg)](https://search.maven.org/artifact/com.2c2p/onetwothreesdk)

## Installation

Download the latest AAR from Maven Central or grab via Gradle:

```kotlin
implementation 'com.2c2p:onetwothreesdk:1.0.0'
```


or Maven:

```xml
<dependency>
    <groupId>com.2c2p</groupId>
    <artifactId>onetwothreesdk</artifactId>
    <version>1.0.0</version>
    <type>aar</type>
</dependency>
```

## Usage

Setup using the following snippet, e.g. in your MyApplication.kt onCreate method:

```kotlin
OneTwoThreeSDKService.initialize(
            checkSumKey = "", // Checksum key
            publicKey = "", // Public key
            privateKey = "", // Private key
            passphrase = "", // Passphrase for private key
            bksPassphrase = "" // BKS passphrase,
        )
```

### Sample

```kotlin

val merchant = Merchant(
    id = "merchant@shopping.com",
    redirectURL = "onetwothreeapp://",
    notificationURL = "https://uat2.123.co.th/DemoShopping/apicallurl.aspx",
    merchantData = listOf(
        MerchantData(item = "943-cnht302gg"),
        MerchantData(item = "FH403"),
        MerchantData(item = "10,000.00"),
        MerchantData(item = "Ref. 43, par. 7")
    )
)

val transaction = Transaction(
    merchantReference = "309321249653",
    preferredAgent = "SCB",
    productDesc = "Description of the product.",
    amount = "100",
    currencyCode = "THB",
    paymentInfo = "",
    paymentExpiry = "2021-12-10 11:21:36"
)

val buyer = Buyer(
    email = "example@gmail.com",
    mobile = "09912345678",
    language = "EN",
    notifyBuyer = true,
    title = "Mr",
    firstName = "Bruce",
    lastName = "Wayne"
)

OneTwoThreeSDKService.startDeeplink(merchant, transaction, buyer, object :
    APIResponseCallback<StartDeeplinkResponse> {
    override fun onResponse(response: StartDeeplinkResponse) {
        // TODO: Success

        try {
            // NOTE: Redirect to bank app using 'deeplinkURL' from the response.
            val launcher = Intent(Intent.ACTION_VIEW)
            launcher.data = Uri.parse(response.deeplinkURL)
            startActivity(launcher)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onFailure(error: Throwable?) {
        // TODO: Failed
    }
})
```

## Contributing
2C2P

## License
[MIT](https://choosealicense.com/licenses/mit/)