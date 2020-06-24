package com.mamingjuju.foodpicker.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

class SpinnerArrowView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val desiredWidth = suggestedMinimumWidth + paddingLeft + paddingRight
        val desiredHeight = suggestedMinimumHeight + paddingTop + paddingBottom

        setMeasuredDimension(
            measureDimension(desiredWidth, widthMeasureSpec),
            measureDimension(desiredHeight, heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas) {
        val centerOfCanvas = Point(width / 2, height / 2)
        val squareSize: Float = (width * 0.9).toFloat()

        val arrowPath = Path()
        arrowPath.moveTo(centerOfCanvas.x.toFloat(), (centerOfCanvas.y - squareSize * 0.15).toFloat())
        arrowPath.lineTo((centerOfCanvas.x + squareSize * 0.05).toFloat(), (centerOfCanvas.y - squareSize * 0.07).toFloat())
        arrowPath.lineTo((centerOfCanvas.x - squareSize * 0.05).toFloat(), (centerOfCanvas.y - squareSize * 0.07).toFloat())
        arrowPath.close()

        val arrowPaint = Paint()
        arrowPaint.apply {
            style = Paint.Style.FILL
            color = Color.WHITE
        }

        val midCirclePaint = Paint().apply {
            color = Color.parseColor("#FFFFFF")
        }

        canvas.drawCircle(centerOfCanvas.x.toFloat(), centerOfCanvas.y.toFloat(), (squareSize * 0.1).toFloat(), midCirclePaint)
        canvas.drawPath(arrowPath, arrowPaint)
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
                result = Math.min(result, specSize)
            }
        }
        if (result < desiredSize) {
            Log.e("ChartView", "The view is too small, the content might get cut")
        }
        return result
    }
}