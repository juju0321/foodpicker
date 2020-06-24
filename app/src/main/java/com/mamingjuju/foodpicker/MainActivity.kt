package com.mamingjuju.foodpicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mamingjuju.foodpicker.data.SpinnerItem
import com.mamingjuju.foodpicker.fragments.AddNewItem
import com.mamingjuju.foodpicker.models.ItemListModel

class MainActivity : AppCompatActivity() {

    private val viewModel: ItemListModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
