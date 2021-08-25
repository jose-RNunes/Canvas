package com.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.extensions.getCompactColor
import kotlin.math.abs

class LineUpView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    val grassPaint = Paint()

    val linesPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 10f
        isAntiAlias = true
    }

    val lineFillPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        strokeWidth = 10f
        isAntiAlias = true
    }

    val rowHeight = 110f

    val padding = 30f

    val greenColor = context.getCompactColor(R.color.green)
    val darkGreenColor = context.getCompactColor(R.color.green_dark)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawFootballFields(it)
            drawLines(it)
            drawGoalLines(it)
            drawCorners(it)
            drawMiddleCircles(it)
        }

    }

    private fun drawFootballFields(canvas: Canvas) {
        val lines = abs((height / 2) / rowHeight)
        (0..abs(lines).toInt()).forEach { value ->
            val top = rowHeight * value
            grassPaint.color = greenColor.takeIf { (value % 2) == 0 } ?: darkGreenColor
            canvas.drawRect(0f, top, width.toFloat(), top + rowHeight, grassPaint)
        }
    }

    private fun drawLines(canvas: Canvas) {
        val lines = abs((height / 2) / rowHeight) +1
        canvas.drawRect(
            padding,
            padding,
            width.toFloat() - padding,
            (lines) * rowHeight -(padding * 2),
            linesPaint
        )
    }

    private fun drawGoalLines(canvas: Canvas) {
        canvas.drawRect(
            (width / 2) - 200f,
            padding,
            (width / 2) + 200f,
            padding + 100,
            linesPaint
        )

        canvas.drawRect(
            (width / 2) - 80f,
            padding,
            (width / 2) + 80f,
            padding + 40f,
            linesPaint
        )
    }

    private fun drawCorners(canvas: Canvas) {
        val cornerSize = 20f
        canvas.drawArc(
            padding - cornerSize,
            padding - cornerSize,
            padding + cornerSize,
            padding + cornerSize,
            0f,
            90f,
            false,
            linesPaint
        )

        canvas.drawArc(
            width - padding - cornerSize,
            (padding - cornerSize),
            width - padding + cornerSize,
            (padding + cornerSize),
            90f,
            90f,
            false,
            linesPaint
        )
    }

    private fun drawMiddleCircles(canvas: Canvas) {
        val height = (abs((height / 2) / rowHeight) +1) * rowHeight
        val circleSize = 140f
        val middleCircleSize = 20f
        canvas.drawArc(
            (width / 2 - circleSize),
            (height) - (padding *2 ) - circleSize,
            (width/2) + circleSize,
            height - (padding *2)  + circleSize,
            180f,
            180f,
            false,
            linesPaint
        )

        canvas.drawArc(
            (width / 2 - middleCircleSize),
            (height) - (padding *2 ) - middleCircleSize,
            (width/2) + middleCircleSize,
            height - (padding *2)  + middleCircleSize,
            180f,
            180f,
            false,
            lineFillPaint
        )

    }
}