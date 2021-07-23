package com.ccpp.onetwothreedemo.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ccpp.onetwothreedemo.R
import com.ccpp.onetwothreedemo.model.Product


class ListItemRecyclerViewAdapter(
    private val values: List<Product>
) : RecyclerView.Adapter<ListItemRecyclerViewAdapter.ViewHolder>() {

    private var clickListener: OnClickProductListener? = null

    fun setOnClickListener(listener: (Product?) -> Unit) {
        clickListener = object : OnClickProductListener {
            override fun onClick(product: Product?) {
                listener(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val item = values[position]
            holder.bind(item)
        } catch (e: Exception) {
            holder.bind(null)
        }
    }

    override fun getItemCount(): Int = 4 //values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val card1: View = view.findViewById(R.id.card1)
        private val card2: View = view.findViewById(R.id.card2)
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        private val title: TextView = view.findViewById(R.id.title)
        private val content: TextView = view.findViewById(R.id.content)
        private val price: TextView = view.findViewById(R.id.price)
        private val btnBuy: TextView = view.findViewById(R.id.btnBuy)

        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Product?) {

            if (absoluteAdapterPosition == 3 || item == null) {
                card1.visibility = View.GONE
                card2.visibility = View.VISIBLE
                view.setOnClickListener { clickListener?.onClick(null) }
                return
            }

            title.text = item.name
            content.text = item.desc
            price.text = "฿${item.formattedPrice}"
            imageView.setImageDrawable(ContextCompat.getDrawable(view.context, item.image))

            view.setOnClickListener {
                clickListener?.onClick(item)
            }
            btnBuy.setOnClickListener {
                clickListener?.onClick(item)
            }
        }
    }
}