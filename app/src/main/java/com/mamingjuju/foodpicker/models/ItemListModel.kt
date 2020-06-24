package com.mamingjuju.foodpicker.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mamingjuju.foodpicker.data.SpinnerItem
import com.mamingjuju.foodpicker.fragments.SpinnerItemList

class ItemListModel: ViewModel() {

    val items: MutableLiveData<List<SpinnerItem>> = MutableLiveData()

    fun getItems(): List<SpinnerItem>? {
        return items.value
    }

    fun setItems(spinnerItemList: MutableList<SpinnerItem>?) {
        Log.i("${ItemListModel::class.simpleName}", "$spinnerItemList")
        items.postValue(spinnerItemList)
    }

}
