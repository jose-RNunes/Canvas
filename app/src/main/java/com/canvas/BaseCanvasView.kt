package com.canvas

import android.content.Context
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

abstract class BaseCanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    protected val contentRect = RectF()

    protected val contentPaint = Paint().apply {
        isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateContentRect()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        updateContentRect()
    }

    private fun updateContentRect() {
        contentRect.set(
            paddingStart.toFloat() + contentPaint.strokeWidth,
            paddingTop.toFloat() + contentPaint.strokeWidth,
            (width - paddingEnd).toFloat() - contentPaint.strokeWidth,
            (height - paddingBottom).toFloat() - contentPaint.strokeWidth
        )
    }
}