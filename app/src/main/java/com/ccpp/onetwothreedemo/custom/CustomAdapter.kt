package com.ccpp.onetwothreedemo.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.ccpp.onetwothreedemo.R
import com.ccpp.onetwothreesdk.model.Buyer
import com.ccpp.onetwothreesdk.model.Merchant
import com.ccpp.onetwothreesdk.model.Transaction
import com.google.android.material.textfield.TextInputLayout

class CustomAdapter(private val dataList:List<InputType>) :
    RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.custom_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(viewHolder: CustomViewHolder, position: Int) {
        viewHolder.bind(dataList[position])
    }

    fun getValueFromIndex(type: InputType): String {
        return dataList.firstOrNull { it == type }?.value.toString()
    }

    fun setValue(merchant: Merchant, transaction: Transaction, buyer: Buyer) {
        dataList.forEach { 
            when(it) {
                InputType.merchantId ->         { it.value = merchant.id }
                InputType.redirectURL ->        { it.value = merchant.redirectURL }
                InputType.notificationURL ->    { it.value = merchant.notificationURL.toString() }
                InputType.merchantData ->       {
                    it.value = merchant.merchantData?.joinToString (":") { data ->
                        data.item.toString()
                    }.toString()
                }
                InputType.merchantReference ->  { it.value = transaction.merchantReference }
                InputType.preferredAgent ->     { it.value = transaction.preferredAgent }
                InputType.productDesc ->        { it.value = transaction.productDesc }
                InputType.paymentInfo ->        { it.value = transaction.paymentInfo.toString() }
                InputType.amount ->             { it.value = transaction.amount }
                InputType.currencyCode ->       { it.value = transaction.currencyCode }
                InputType.paymentExpiry ->      { it.value = transaction.paymentExpiry.toString() }
                InputType.userDefined1 ->       { it.value = transaction.userDefined1.toString() }
                InputType.userDefined2 ->       { it.value = transaction.userDefined2.toString() }
                InputType.userDefined3 ->       { it.value = transaction.userDefined3.toString() }
                InputType.userDefined4 ->       { it.value = transaction.userDefined4.toString() }
                InputType.userDefined5 ->       { it.value = transaction.userDefined5.toString() }
                InputType.buyerEmail ->         { it.value = buyer.email }
                InputType.buyerMobile ->        { it.value = buyer.mobile }
                InputType.buyerLanguage ->      { it.value = buyer.language }
                InputType.buyerOS ->            { it.value = buyer.os }
                InputType.buyerNotify ->        { it.value = if (buyer.notifyBuyer) "true" else "false" }
                InputType.buyerTitle ->         { it.value = buyer.title.toString() }
                InputType.buyerFirstName ->     { it.value = buyer.firstName.toString() }
                InputType.buyerLastName ->      { it.value = buyer.lastName.toString() }
            }
        }
        notifyDataSetChanged()
    }

    inner class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val til: TextInputLayout = view.findViewById(R.id.textInputLayout)
        private val editText: EditText = view.findViewById(R.id.editText)

        fun bind(model: InputType) {
            til.hint = model.title
            editText.hint = model.hint
            editText.setText(model.value)
            editText.doAfterTextChanged { model.value = it.toString() }
        }
    }
}
