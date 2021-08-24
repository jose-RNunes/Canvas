package com.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import com.extensions.px

class RoundedDashView constructor(
    context: Context,
    attributeSet: AttributeSet?
) : BaseCanvasView(context, attributeSet) {

    private val cornerRadius = 8.px.toFloat()

    private val dashWidth = 10f

    private val dashGap = 10f

    init {
        contentPaint.style = Paint.Style.STROKE
        contentPaint.strokeWidth = 2.px.toFloat()
        contentPaint.pathEffect = DashPathEffect(floatArrayOf(50f, 10f, 20f, 10f), 80f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        contentRect.inset(cornerRadius, cornerRadius)

        canvas?.drawLine(contentRect.left, contentRect.top, contentRect.right, contentRect.top, contentPaint)
        /*
        canvas?.drawRoundRect(
            contentRect,
            cornerRadius,
            cornerRadius,
            contentPaint
        )

         */
    }
}