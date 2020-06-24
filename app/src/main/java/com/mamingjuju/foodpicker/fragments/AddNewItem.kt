package com.mamingjuju.foodpicker.fragments


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mamingjuju.foodpicker.R
import com.mamingjuju.foodpicker.data.SpinnerItem
import com.mamingjuju.foodpicker.models.AddNewItemModel
import com.mamingjuju.foodpicker.models.ItemListModel


class AddNewItem : Fragment() {

    private val itemListModel: ItemListModel by activityViewModels()
    private val addNewItemModel: AddNewItemModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_new_item, container, false)
        val arguments = arguments
        var spinnerItemNewImageResource: Int = 0
        var spinnerItemNewItemName: String = ""

        val newItemImage: ImageView = rootView.findViewById(R.id.newItemImage)
        arguments?.let {
            val newItemImageResource = arguments?.getInt("imageResource")
            newItemImage.setImageResource(newItemImageResource)
            spinnerItemNewImageResource = newItemImageResource
        }
        newItemImage.setOnClickListener {
            findNavController().navigate(R.id.action_addNewItem_to_itemImageFragment)
        }

        val saveButton = rootView.findViewById<Button>(R.id.saveButton)
        val newItemEditText: EditText = rootView.findViewById(R.id.newItemEditText)
        newItemEditText.addTextChangedListener {
            if (newItemEditText.text.toString().isNotEmpty()) {
                spinnerItemNewItemName = newItemEditText.text.toString()
            }
            if(spinnerItemNewItemName.isNotEmpty() && spinnerItemNewImageResource != 0) {
                saveButton.isEnabled = true
            }
        }


        saveButton.setOnClickListener {
            addNewItemModel.getOriginFragment()?.let { it -> findNavController().navigate(it) }
            val currentItemListModelValue = itemListModel.items.value
            val newItemList = mutableListOf<SpinnerItem>()
            val inputManager: InputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(newItemEditText.windowToken,0)
            if (currentItemListModelValue != null) {
                newItemList.addAll(currentItemListModelValue)
            }
            newItemList.add(SpinnerItem(spinnerItemNewItemName, spinnerItemNewImageResource))
            itemListModel.items.value = newItemList
        }

        val cancelButton = rootView.findViewById<Button>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            addNewItemModel.getOriginFragment()?.let { it -> findNavController().navigate(it) }
        }

        return rootView
    }

}
