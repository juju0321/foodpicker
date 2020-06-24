package com.mamingjuju.foodpicker.views

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


class SpinnerWheelView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val wheelItems = mutableListOf<String>()
    private var sweepAngle = 0f

    private val textPaint = TextPaint().apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#F5F5F5")
        textAlign = Paint.Align.LEFT
        textSize = 32f
        typeface = Typeface.create("Arial", Typeface.BOLD)
    }

    private val wheelColorList = listOf(
        "#FEC8A0", "#FEA562", "#FD7713", "#FEA562"
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.v("Chart onMeasure w", MeasureSpec.toString(widthMeasureSpec))
        Log.v("Chart onMeasure h", MeasureSpec.toString(heightMeasureSpec))


        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        val widthAndHeightSpec = min(desiredWidth, desiredHeight)

        setMeasuredDimension(
            measureDimension(desiredWidth, widthAndHeightSpec),
            measureDimension(desiredHeight, widthAndHeightSpec)
        )
    }

    override fun onDraw(canvas: Canvas) {
        val centerOfCanvas = Point(width / 2, height / 2)
        val squareSize: Float = (min(width, height) * 0.7).toFloat()
        val secondarySquareSize = (min(width, height) * 0.75).toFloat()
        val rectF = RectF((centerOfCanvas.x - squareSize / 2),
            (centerOfCanvas.y - squareSize / 2),
            (centerOfCanvas.x + squareSize / 2),
            (centerOfCanvas.y + squareSize / 2))

        val secondaryRectF = RectF((centerOfCanvas.x - secondarySquareSize / 2),
            (centerOfCanvas.y - secondarySquareSize / 2),
            (centerOfCanvas.x + secondarySquareSize / 2),
            (centerOfCanvas.y + secondarySquareSize / 2))

        canvas.drawCircle(centerOfCanvas.x.toFloat(), centerOfCanvas.y.toFloat(), squareSize/2, Paint().apply { style = Paint.Style.FILL
            color = Color.WHITE})

        if(wheelItems.size != 0) {
            drawArcAndSetDegreeToWheelItemsMapping(canvas, rectF, secondaryRectF, centerOfCanvas, squareSize)
        }
        else {
            sweepAngle = 45f
            drawArcOnly(canvas, rectF, secondaryRectF, centerOfCanvas, squareSize)
        }
    }

    fun addWheelItems(newItemList: List<String>) {
        wheelItems.clear()
        wheelItems.addAll(newItemList)
        sweepAngle = (360f / wheelItems.size)
        invalidate()
    }

    fun addSingleItem(item: String) {
        wheelItems.add(item)
        sweepAngle = (360f / wheelItems.size)
        invalidate()
    }

    fun getWheelItems(): MutableList<String> {
        return wheelItems
    }

    private fun measureDimension(desiredSize: Int, measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = desiredSize
            if (specMode == MeasureSpec.AT_MOST) {
                result = min(result, specSize)
            }
        }
        if (result < desiredSize) {
            Log.e("ChartView", "The view is too small, the content might get cut")
        }
        return result
    }

    private fun drawArcAndSetDegreeToWheelItemsMapping(canvas: Canvas, rectF: RectF, secondaryRectF: RectF, centerOfCanvas: Point, squareSize: Float) {
        var colorIndex = 0
        var arcPaint = Paint()
        var startAngle = 0f

        this.wheelItems.forEach { item ->
            if(colorIndex == wheelColorList.size) {
                colorIndex = 0
            }

            arcPaint.color = Color.parseColor(wheelColorList.elementAt(colorIndex++))
            arcPaint.alpha = 150
            canvas.drawArc(secondaryRectF, startAngle, sweepAngle, true, arcPaint)
            arcPaint.alpha = 255
            canvas.drawArc(rectF, startAngle, sweepAngle, true, arcPaint)

            var point = this.findPoint(startAngle + (sweepAngle/2), squareSize/2)
            val myPath = Path()
            myPath.moveTo(centerOfCanvas.x + point.x.toFloat(), centerOfCanvas.y + point.y.toFloat())
            myPath.lineTo(centerOfCanvas.x.toFloat(), centerOfCanvas.y.toFloat())
            canvas.drawTextOnPath("$item", myPath, (squareSize/2) * 0.05f, 10f, textPaint)
            startAngle += sweepAngle
        }
    }

    private fun drawArcOnly(canvas: Canvas, rectF: RectF, secondaryRectF: RectF, centerOfCanvas: Point, squareSize: Float) {
        var colorIndex = 0
        var arcPaint = Paint()
        var startAngle = 0f

        for(i in 1..8) {
            if(colorIndex == wheelColorList.size) {
                colorIndex = 0
            }

            arcPaint.color = Color.parseColor(wheelColorList.elementAt(colorIndex++))
            arcPaint.alpha = 150
            canvas.drawArc(secondaryRectF, startAngle, sweepAngle, true, arcPaint)
            arcPaint.alpha = 255
            canvas.drawArc(rectF, startAngle, sweepAngle, true, arcPaint)

            startAngle += sweepAngle
        }
    }

    private fun findPoint(arcAngle: Float, radius: Float) : Point {
        val point = Point()

        when (arcAngle) {
            0f, 360f -> {
                point.apply {
                    x = radius.toInt()
                    y = 0
                }
            }
            90f -> {
                point.apply {
                    x = 0
                    y = radius.toInt()
                }
            }
            180f -> {
                point.apply {
                    x = -radius.toInt()
                    y = 0
                }
            }
            270f -> {
                point.apply {
                    x = 0
                    y = -radius.toInt()
                }
            }
            else -> {
                if(arcAngle > 0 && arcAngle < 90) {
                    point.apply {
                        x = (radius * cos((Math.PI/180) * arcAngle)).toInt()
                        y = (radius * sin((Math.PI/180) * arcAngle)).toInt()
                    }
                }

                if(arcAngle > 90 && arcAngle < 180) {
                    point.apply {
                        x = -(radius * cos((Math.PI/180) * (180 - arcAngle))).toInt()
                        y = (radius * sin((Math.PI/180) * (180 - arcAngle))).toInt()
                    }
                }

                if(arcAngle > 180 && arcAngle < 270){
                    point.apply {
                        x = -(radius * cos((Math.PI/180) * (arcAngle - 180))).toInt()
                        y = -(radius * sin((Math.PI/180) * (arcAngle - 180))).toInt()
                    }
                }

                if(arcAngle > 270) {
                    point.apply {
                        x = (radius * cos((Math.PI/180) * (360 - arcAngle))).toInt()
                        y = -(radius * sin((Math.PI/180) * (360 - arcAngle))).toInt()
                    }
                }

            }
        }
        return point
    }
}