package com.ccpp.onetwothreedemo.detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ccpp.onetwothreedemo.R
import com.ccpp.onetwothreedemo.databinding.FragmentDetailBinding
import com.ccpp.onetwothreedemo.model.Product
import com.ccpp.onetwothreedemo.util.LoadingDialog
import com.ccpp.onetwothreedemo.util.showErrorDialog


class DetailFragment : Fragment() {

    private val model: DetailViewModel by viewModels()
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingDialog = LoadingDialog(requireContext())
        model.product = requireArguments().getParcelable(PRODUCT)!!
        model.event.observe(this, ::onViewModelEvent)
        model.isInProgress.observe(this) {
            if (it) {
                loadingDialog?.show()
            } else {
                loadingDialog?.dismiss()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentDetailBinding.inflate(inflater, container, false).apply {
            viewModel = model
            lifecycleOwner = this@DetailFragment

            toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

            price.text = "฿${model.product?.formattedPrice}"
        }
        return view.root
    }

    private fun onViewModelEvent(event: DetailViewModel.Event) {
        when (event) {
            is DetailViewModel.Event.OnPaySuccess -> {
                try {
                    val launcher = Intent(Intent.ACTION_VIEW)
                    launcher.data = Uri.parse(event.res.deeplinkURL)
                    startActivity(launcher)
                } catch (e: Exception) {
                    showErrorDialog(
                        title = "Unable to open ${model.paymentMethod.value?.name} app",
                        message = "Please ensure you have the app downloaded on your device.",
                    )
                }
            }
            is DetailViewModel.Event.OnPayFailed -> {
                showErrorDialog("Failed", event.error?.localizedMessage.toString())
            }
        }
    }

    companion object {
        private const val PRODUCT = "product"

        fun newInstance(product: Product): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PRODUCT, product)
                }
            }
        }
    }
}