package com.mamingjuju.foodpicker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mamingjuju.foodpicker.R
import com.mamingjuju.foodpicker.data.SpinnerItem

class ItemListRecyclerViewAdapter(): RecyclerView.Adapter<ItemListRecyclerViewAdapter.ViewHolder>() {

    private val spinnerItems = mutableListOf<SpinnerItem>()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView? =itemView.findViewById(R.id.itemImageView)
        val textView: TextView = itemView.findViewById(R.id.itemTextView)
    }

    fun submitList(newItems: List<SpinnerItem>) {
        spinnerItems.clear()
        spinnerItems.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_display_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return spinnerItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView?.setImageResource(spinnerItems[position].itemImage)
        holder.textView?.text = spinnerItems[position].itemName
    }
}