package com.mamingjuju.foodpicker.fragments


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mamingjuju.foodpicker.R
import com.mamingjuju.foodpicker.adapter.ItemListRecyclerViewAdapter
import com.mamingjuju.foodpicker.data.SpinnerItem
import com.mamingjuju.foodpicker.models.AddNewItemModel
import com.mamingjuju.foodpicker.models.ItemListModel


/**
 * A simple [Fragment] subclass.
 */
class SpinnerItems : Fragment() {

    private val itemListModel: ItemListModel by activityViewModels()
    private val addNewItemModel: AddNewItemModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_spinner_items, container, false)

        val addButton = rootView.findViewById<FloatingActionButton>(R.id.floating_action_button)
        addButton.setOnClickListener {
            addNewItemModel.setOriginFragment(R.id.action_addNewItem_to_spinnerItems)
            findNavController().navigate(R.id.action_spinnerItems_to_addNewItem)
        }

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.itemListRecyclerView)

        val linearLayout = LinearLayoutManager(activity?.applicationContext)
        linearLayout.orientation = LinearLayoutManager.VERTICAL

        val recyclerViewAdapter = ItemListRecyclerViewAdapter()
        recyclerView.layoutManager = linearLayout
        recyclerView.adapter = recyclerViewAdapter

        val spinnerItemObserver = Observer<List<SpinnerItem>> { spinnerItems ->
            recyclerViewAdapter.submitList(spinnerItems)
        }

        val goBackToWheelButton = rootView.findViewById<ImageButton>(R.id.goBackToWheelButton)
        goBackToWheelButton.setOnClickListener {
            findNavController().navigate(R.id.action_spinnerItems_to_spinnerWheel)
        }

        itemListModel.items.observe(viewLifecycleOwner, spinnerItemObserver)
        return rootView
    }

}
