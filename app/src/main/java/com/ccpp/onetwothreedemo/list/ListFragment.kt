package com.ccpp.onetwothreedemo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccpp.onetwothreedemo.R
import com.ccpp.onetwothreedemo.custom.CustomFragment
import com.ccpp.onetwothreedemo.databinding.FragmentListBinding
import com.ccpp.onetwothreedemo.detail.DetailFragment


class ListFragment : Fragment() {

    private val model: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentListBinding.inflate(inflater, container, false).apply {
            viewModel = model
            lifecycleOwner = this@ListFragment

            list.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ListItemRecyclerViewAdapter(model.getItems()).apply {
                    setOnClickListener { product ->
                        val fragment = if (product == null) {
                            CustomFragment.newInstance()
                        } else {
                            DetailFragment.newInstance(product)
                        }
                        // next
                        requireActivity().supportFragmentManager.beginTransaction().apply {
                            replace(R.id.fragment_container, fragment)
                            addToBackStack(DetailFragment::class.java.simpleName)
                            commit()
                        }
                    }
                }
            }
        }
        return view.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ListFragment().apply {
                arguments = Bundle().apply {
                    // skip
                }
            }
    }
}