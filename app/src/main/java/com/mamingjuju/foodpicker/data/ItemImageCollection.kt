package com.mamingjuju.foodpicker.data

import android.util.Log
import com.mamingjuju.foodpicker.R

object ItemImageCollection {
    private val defaultImageIconList : List<Int> = listOf(
        R.drawable.apple,
        R.drawable.breakfast,
        R.drawable.burger,
        R.drawable.cake_slice,
        R.drawable.fried_chicken,
        R.drawable.meat,
        R.drawable.ramen,
        R.drawable.sandwich
    )
    fun getDefaultImageIconList(): List<Int> {
        Log.i("${ItemImageCollection::class.simpleName}", "getDefaultImageIconList()")
        return defaultImageIconList
    }
}