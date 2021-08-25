package com.canvas

import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.graphics.toRectF


class CurvedDrawable constructor(
    val backgroundColor: Int
) : Drawable() {

    private val path = Path()

    private val paint = Paint().apply {
        isAntiAlias = true
        color = backgroundColor
    }

    init {
        drawCurvedButtonPath()
    }

    private fun drawCurvedButtonPath() {
        val bounds = bounds
        path.addRect(bounds.toRectF(), Path.Direction.CW)
    }

    override fun draw(canvas: Canvas) {
        val bounds = bounds
        val pathCurve = bounds.bottom.toFloat() / 2
        val centerX = bounds.centerX().toFloat()
        path.moveTo(0f, 0f)
        path.quadTo(centerX, pathCurve, bounds.right.toFloat(), 0f)
        path.lineTo(bounds.right.toFloat(), bounds.bottom.toFloat())
        path.lineTo(0f, bounds.bottom.toFloat())
        path.close()
        canvas.drawPath(path, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}