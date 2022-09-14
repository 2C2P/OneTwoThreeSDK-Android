# OneTwoThreeSDK for Android

![Minimum SDK Version](https://img.shields.io/badge/minSdkVersion-23-brightgreen)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.2c2p/onetwothreesdk/badge.svg)](https://search.maven.org/artifact/com.2c2p/onetwothreesdk)

## Installation

Download the latest AAR from Maven Central or grab via Gradle:

```kotlin
implementation 'com.2c2p:onetwothreesdk:1.0.2'
```


or Maven:

```xml
<dependency>
    <groupId>com.2c2p</groupId>
    <artifactId>onetwothreesdk</artifactId>
    <version>1.0.2</version>
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
            startActivityForResult(launcher, 1000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onFailure(error: Throwable?) {
        // TODO: Failed
    }
})
```

## Proguard

Please provide this into your `proguard-rules.pro` for release mode

```

-keep class org.spongycastle.** { *; }

#################### Retrofit ######################

# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Keep annotation default values (e.g., retrofit2.http.Field.encoded).
-keepattributes AnnotationDefault

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# Keep generic signature of Call (R8 full mode strips signatures from non-kept items).
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
```

## Contributing
2C2P

## License
[MIT](https://choosealicense.com/licenses/mit/)