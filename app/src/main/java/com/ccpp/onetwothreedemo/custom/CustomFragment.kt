package com.ccpp.onetwothreedemo.custom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccpp.onetwothreedemo.constant.Constants
import com.ccpp.onetwothreedemo.databinding.FragmentCustomBinding
import com.ccpp.onetwothreedemo.util.LoadingDialog
import com.ccpp.onetwothreedemo.util.showErrorDialog
import com.ccpp.onetwothreesdk.model.Buyer
import com.ccpp.onetwothreesdk.model.Merchant
import com.ccpp.onetwothreesdk.model.MerchantData
import com.ccpp.onetwothreesdk.model.Transaction
import com.google.gson.GsonBuilder


class CustomFragment : Fragment() {

    private val model: CustomViewModel by viewModels()
    private lateinit var customAdapter: CustomAdapter

    private val items = InputType.values().toList()
    private var loadingDialog: LoadingDialog? = null
    private var merchant: Merchant? = null
    private var transaction: Transaction? = null
    private var buyer: Buyer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingDialog = LoadingDialog(requireContext())
        model.event.observe(this, ::onViewModelEvent)
        model.isInProcess.observe(this) {
            if (it) {
                loadingDialog?.show()
            } else {
                loadingDialog?.dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCustomBinding.inflate(inflater, container, false).apply {
            viewModel = model
            lifecycleOwner = this@CustomFragment

            toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

            customAdapter = CustomAdapter(items)
            customList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = customAdapter
            }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setCustomValue()
    }

    private fun setCustomValue() {

        // TODO: Add information for Confirm
        merchant = Merchant(
            id = "merchant@shopping.com",
            redirectURL = Constants.appScheme,
            notificationURL = "https://uat2.123.co.th/DemoShopping/apicallurl.aspx",
            merchantData = listOf(
                MerchantData(item = "943-cnht302gg"),
                MerchantData(item = "FH403"),
                MerchantData(item = "10,000.00"),
                MerchantData(item = "Ref. 43, par. 7")
            )
        )

        transaction = Transaction(
            merchantReference = "309321249653",
            preferredAgent = "SCB",
            productDesc = "Description of the product.",
            amount = "100",
            currencyCode = "THB",
            paymentInfo = "",
            paymentExpiry = "2021-12-10 11:21:36"
        )

        buyer = Buyer(
            email = "example@gmail.com",
            mobile = "09912345678",
            language = "EN",
            notifyBuyer = true,
            title = "Mr",
            firstName = "Bruce",
            lastName = "Wayne"
        )

        customAdapter.setValue(merchant!!, transaction!!, buyer!!)
    }

    private fun assignValue(type: InputType, value: String) {
        when(type) {
            InputType.merchantId        -> { merchant?.id = value }
            InputType.redirectURL       -> { merchant?.redirectURL = value }
            InputType.notificationURL   -> { merchant?.notificationURL = value }
            InputType.merchantData      -> { merchant?.merchantData = value.split(":").map { data -> MerchantData(data) } }
            InputType.merchantReference -> { transaction?.merchantReference = value }
            InputType.preferredAgent    -> { transaction?.preferredAgent = value }
            InputType.productDesc       -> { transaction?.productDesc = value }
            InputType.paymentInfo       -> { transaction?.paymentInfo = value }
            InputType.amount            -> { transaction?.amount = value }
            InputType.currencyCode      -> { transaction?.currencyCode = value }
            InputType.paymentExpiry     -> { transaction?.paymentExpiry = value }
            InputType.userDefined1      -> { transaction?.userDefined1 = value }
            InputType.userDefined2      -> { transaction?.userDefined2 = value }
            InputType.userDefined3      -> { transaction?.userDefined3 = value }
            InputType.userDefined4      -> { transaction?.userDefined4 = value }
            InputType.userDefined5      -> { transaction?.userDefined5 = value }
            InputType.buyerEmail        -> { buyer?.email = value }
            InputType.buyerMobile       -> { buyer?.mobile = value }
            InputType.buyerLanguage     -> { buyer?.language = value }
            InputType.buyerOS           -> { buyer?.os = value }
            InputType.buyerNotify       -> { buyer?.notifyBuyer = (value.lowercase() == "true") }
            InputType.buyerTitle        -> { buyer?.title = value }
            InputType.buyerFirstName    -> { buyer?.firstName = value }
            InputType.buyerLastName     -> { buyer?.lastName = value }
        }
    }

    private fun onViewModelEvent(event: CustomViewModel.Event) {
        when(event) {
            CustomViewModel.Event.OnClickConfirm -> {
                items.forEach { type ->
                    val value = customAdapter.getValueFromIndex(type)
                    assignValue(type, value)
                }

                val gson = GsonBuilder().setPrettyPrinting().create()
                Log.d("DEMO", "Merchant: ${gson.toJson(merchant)}")
                Log.d("DEMO", "Transaction: ${gson.toJson(transaction)}")
                Log.d("DEMO", "Buyer: ${gson.toJson(buyer)}")

                /// Call 123 service...
                model.callService(merchant!!, transaction!!, buyer!!)
            }
            is CustomViewModel.Event.OnConfirmSuccess -> {
                try {
                    val launcher = Intent(Intent.ACTION_VIEW)
                    launcher.data = Uri.parse(event.res.deeplinkURL)
                    startActivity(launcher)
                } catch (e: Exception) {
                    showErrorDialog(
                        title = "Unable to open ${transaction?.preferredAgent} app",
                        message = "Please ensure you have the app downloaded on your device.",
                    )
                }
            }
            is CustomViewModel.Event.OnConfirmFailed -> {
                showErrorDialog("Failed", event.error?.localizedMessage.toString())
            }
        }
    }

    companion object {
        fun newInstance() =
            CustomFragment().apply {
                arguments = Bundle().apply {
                    //
                }
            }
    }
}