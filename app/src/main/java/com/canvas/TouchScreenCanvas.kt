package com.canvas

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View

class TouchScreenCanvas @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val path = Path()
    private val paint = Paint().apply {
        strokeWidth = 3.toFloat()
        style = Paint.Style.STROKE
    }

    private val gestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent?): Boolean {
                Log.i("TAG", "Double Tap")
                path.reset()
                return true
            }
        })

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)

        when (event?.action) {
            // A gesture is starting, move the path to the pointer's location
            MotionEvent.ACTION_DOWN -> path.moveTo(event.x, event.y)
                .also { Log.i("TAG", "Start gesture") }

            // Follow the pointer as it moves
            MotionEvent.ACTION_MOVE -> path.lineTo(event.x, event.y)
                .also { Log.i("TAG", "Move gesture  ${event.x} - ${event.y}") }

            // A gesture finished, stop drawing
            MotionEvent.ACTION_UP -> Log.i("TAG", "Finish gesture")

            else -> return false
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawPath(path, paint)
    }

}