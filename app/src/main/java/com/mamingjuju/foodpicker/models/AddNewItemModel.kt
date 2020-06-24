package com.mamingjuju.foodpicker.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddNewItemModel: ViewModel() {
    private val originFragment: MutableLiveData<Int> = MutableLiveData()

    fun getOriginFragment(): Int? {
        return originFragment.value
    }

    fun setOriginFragment(origin: Int) {
        originFragment.value = origin
    }
}