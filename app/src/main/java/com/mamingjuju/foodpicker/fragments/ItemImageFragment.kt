package com.mamingjuju.foodpicker.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mamingjuju.foodpicker.R
import com.mamingjuju.foodpicker.adapter.ItemImageFragmentRecyclerViewAdapter
import com.mamingjuju.foodpicker.data.ItemImageCollection

class ItemImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_item_image_list, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.newItemImageRecyclerView)
        recyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = ItemImageFragmentRecyclerViewAdapter(ItemImageCollection.getDefaultImageIconList())
        }

        return rootView
    }
}