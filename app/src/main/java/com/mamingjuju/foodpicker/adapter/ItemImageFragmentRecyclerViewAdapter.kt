package com.mamingjuju.foodpicker.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mamingjuju.foodpicker.R

class ItemImageFragmentRecyclerViewAdapter(private val itemList: List<Int>): RecyclerView.Adapter<ItemImageFragmentRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.addNewItemImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.i("${ItemImageFragmentRecyclerViewAdapter::class.simpleName}", "${itemList.size}")
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemImage.setImageResource(itemList[position])
        val bundle = bundleOf("imageResource" to itemList[position])
        holder.itemImage.setOnClickListener { view ->
            Log.i("${ItemImageFragmentRecyclerViewAdapter::class.simpleName}", "${itemList[position]}")
            view.findNavController().navigate(R.id.action_itemImageFragment_to_addNewItem, bundle)
        }

    }
}