package com.mamingjuju.foodpicker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mamingjuju.foodpicker.R
import com.mamingjuju.foodpicker.data.SpinnerItem
import com.mamingjuju.foodpicker.models.ItemListModel
import com.mamingjuju.foodpicker.views.SpinnerWheelView
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mamingjuju.foodpicker.models.AddNewItemModel
import kotlin.math.abs
import kotlin.random.Random

class SpinnerWheel : Fragment(), View.OnTouchListener {

    private val itemListModel: ItemListModel by activityViewModels()
    private val addNewItemModel: AddNewItemModel by activityViewModels()
    private val mWheelItems: MutableList<String> = mutableListOf()

    private var x1 = 0f
    private var y1 = 0f
    private var x2 = 0f
    private var y2 = 0f
    private var deltaX = 0f
    private var deltaY = 0f
    private val SWIPE_DISTANCE_THRESHOLD = 100

    private var target = -1
    private val mSpinnerItems: MutableList<SpinnerItem> = mutableListOf()
    private lateinit var spinnerItemResult: SpinnerItem

    private lateinit var spinnerWheelView: SpinnerWheelView
    private lateinit var spinResultImage: ImageView
    private lateinit var spinResultText: TextView
    private var previousDegree = 0
    private var rotateToDegree = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_spinner_wheel, container, false)

        spinnerWheelView = rootView.findViewById(R.id.spinnerWheelView)
        spinnerWheelView.setOnTouchListener(this)

        spinResultImage = rootView.findViewById(R.id.selectedItemImage)
        spinResultText = rootView.findViewById(R.id.selectedItemName)

        val spinnerItemsObserver = Observer<List<SpinnerItem>> { spinnerItems ->
            for (item in spinnerItems) {
                mWheelItems.add(item.itemName)
                mSpinnerItems.add(item)
            }
            spinnerWheelView.addWheelItems(mWheelItems)
        }

        itemListModel.items.observe(viewLifecycleOwner, spinnerItemsObserver)

        if(itemListModel.getItems()?.size == null) {
            spinResultText.text = "Add items first"
        }
        else {
            spinResultText.text = "Spin it"
        }

        val imageButton = rootView.findViewById<ImageView>(R.id.spinnerSettingsButton)
        imageButton.setOnClickListener {
            findNavController().navigate(R.id.action_spinnerWheel_to_spinnerItems)
        }

        val addNewItemFloatingActionButton = rootView.findViewById<FloatingActionButton>(R.id.spinnerWheelFloatingActionButton)
        addNewItemFloatingActionButton.setOnClickListener {
            addNewItemModel.setOriginFragment(R.id.action_addNewItem_to_spinnerWheel)
            findNavController().navigate(R.id.action_spinnerWheel_to_addNewItem)
        }

        return rootView
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        if(mWheelItems.size > 0) {
            setTarget(Random.nextInt(mWheelItems.size))
            when(event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    x1 = event.x
                    y1 = event.y
                }
                MotionEvent.ACTION_UP -> {
                    x2 = event.x
                    y2 = event.y
                    deltaX = x2 - x1
                    deltaY = y2 - y1

                    if(abs(deltaX) > abs(deltaY)) {
                        if(deltaX < 0 && abs(deltaX) > SWIPE_DISTANCE_THRESHOLD) {
                            spinnerWheelView.startAnimation(createRotateAnimation(target))
                        }

                    } else {
                        if(deltaY > 0 && abs(deltaY) > SWIPE_DISTANCE_THRESHOLD) {
                            spinnerWheelView.startAnimation(createRotateAnimation(target))
                        }
                    }
                }
            }
        }

        return true
    }

    private fun setTarget(target: Int) {
        this.target = target
    }

    private fun getAngleOfIndexTarget(target: Int): Float {
        return (360 / mWheelItems.size) * target.toFloat()
    }

    private fun createRotateAnimation(target: Int) : RotateAnimation {
        previousDegree = rotateToDegree % 360
        val centerArcDegree  = (270 - getAngleOfIndexTarget(target) - ((360 / mWheelItems.size) / 2)).toInt()
        val lowerRange = centerArcDegree - ((360/mWheelItems.size) / 2) + 5
        val upperRange = centerArcDegree + ((360/mWheelItems.size) / 2) - 5
        rotateToDegree = Random.nextInt(lowerRange, upperRange)

        val rotateAnimation = RotateAnimation(previousDegree.toFloat(), rotateToDegree.toFloat() + (360*15),
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f)

        rotateAnimation.duration = 8000
        rotateAnimation.fillAfter = true
        rotateAnimation.interpolator = DecelerateInterpolator()
        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                spinnerItemResult = mSpinnerItems.elementAt(target)
                spinResultImage.setImageResource(spinnerItemResult.itemImage)
                spinResultText.text = spinnerItemResult.itemName
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })

        return rotateAnimation
    }

}
