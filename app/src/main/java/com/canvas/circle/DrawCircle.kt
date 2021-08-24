package com.canvas.circle

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class DrawCircle {

    fun drawCircle(canvas: Canvas, paint: Paint, bounds: RectF, radius: Float? = null) {
        val cx = (bounds.right + bounds.left) / 2
        val cy = (bounds.bottom + bounds.top) / 2
        val defaultRadius = getDefaultRadius(bounds)
        canvas.drawCircle(cx, cy, radius ?: defaultRadius, paint)
    }

    fun getDefaultRadius(bounds: RectF): Float {
        return (if (bounds.bottom > bounds.right)
            bounds.right
        else
            bounds.bottom) / 2
    }
}