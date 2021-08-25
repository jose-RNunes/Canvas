package com.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View

class ImageViewWithZoom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var scaleFactor = 1.0f
    private var image: Drawable? = null

    init {
        image = context.getDrawable(R.drawable.ic_logo_orange)
        image?.let {
            it.setBounds(0,0,it.intrinsicWidth, it.intrinsicHeight)
        }
        isFocusable = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // Set the image bounderies
        canvas?.save()
        canvas?.scale(scaleFactor, scaleFactor);
        image?.draw(canvas!!)
        canvas?.restore()
    }

    var firstTouch = true
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)

        when (event?.action) {
            // A gesture is starting, move the path to the pointer's location
            MotionEvent.ACTION_DOWN -> {
                Log.i("TAG", "Start gesture")
                firstTouch = true
            }

            // Follow the pointer as it moves
            MotionEvent.ACTION_MOVE ->{
                if(!firstTouch)
                scaleFactor = event.x / 100


                firstTouch = false

            Log.i("TAG", "Position ${event.x} - ${event.y}")
        }

            // A gesture finished, stop drawing
            MotionEvent.ACTION_UP -> {
                firstTouch = true
                Log.i("TAG", "Finish gesture")
            }

            else -> return false
        }

        invalidate()
        return true
    }

    private val scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.OnScaleGestureListener {
        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            Log.i("TAG", "Scale start")
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
            Log.i("TAG", "Scale end")
        }

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            detector?.let {
                scaleFactor *= detector.scaleFactor

                scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f))
                invalidate()
            }
            return true
        }
    })

}