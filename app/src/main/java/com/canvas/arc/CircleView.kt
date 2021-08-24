package com.canvas.arc

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import com.canvas.BaseCanvasView
import com.canvas.R
import com.extensions.getCompactColor

class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseCanvasView(context, attrs) {

    private val drawCircle = DrawCircle()

    init {
        attrs?.apply {
            val typeArray = context.obtainStyledAttributes(this, R.styleable.CircleView)

            circleType(typeArray.getInt(R.styleable.CircleView_clv_type, 0))

            circleColor(
                typeArray.getColor(
                    R.styleable.CircleView_clv_color,
                    context.getCompactColor(R.color.grey)
                )
            )

            circleStrokeWidth(
                typeArray.getDimension(R.styleable.CircleView_clv_stroke_width, 0f)
            )

            typeArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { drawCircle(canvas) }
    }

    private fun drawCircle(canvas: Canvas) {
        drawCircle.drawCircle(canvas, contentPaint, contentRect)
    }

    private fun circleStrokeWidth(strokeWidth: Float) {
        contentPaint.strokeWidth = strokeWidth
    }

    private fun circleColor(color: Int) {
        contentPaint.color = color
    }

    private fun circleType(circleType: Int) {
        contentPaint.style = when (circleType) {
            0 -> Paint.Style.FILL
            1 -> Paint.Style.STROKE
            else -> Paint.Style.FILL_AND_STROKE
        }
    }
}