package com.mamingjuju.foodpicker.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.mamingjuju.foodpicker.R
import com.mamingjuju.foodpicker.adapter.ItemListRecyclerViewAdapter
import com.mamingjuju.foodpicker.data.SpinnerItem
import com.mamingjuju.foodpicker.models.ItemListModel

class SpinnerItemList : Fragment() {

    private val itemList: MutableList<SpinnerItem> = mutableListOf()
    private lateinit var viewModel: ItemListModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewModel = ViewModelProviders.of(this).get(ItemListModel::class.java)

        val rootView = inflater.inflate(R.layout.fragment_spinner_item_list, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.itemListRecyclerView)
        var recyclerViewAdapter: ItemListRecyclerViewAdapter? = null

        recyclerView.adapter = recyclerViewAdapter
        return rootView

    }
}
