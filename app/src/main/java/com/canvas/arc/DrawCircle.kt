package com.canvas.arc

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import kotlin.math.min

class DrawCircle {

    fun drawCircle(canvas: Canvas, paint: Paint, bounds: RectF, radius: Float? = null) {
        val cx = (bounds.right + bounds.left) / 2
        val cy = (bounds.bottom + bounds.top) / 2
        val defaultRadius = getDefaultRadius(bounds)
        canvas.drawCircle(cx, cy, radius ?: defaultRadius, paint)
    }

    fun getDefaultRadius(bounds: RectF) = min(bounds.right, bounds.bottom) / 2
}