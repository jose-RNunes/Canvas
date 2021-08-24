package com.canvas

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.canvas.circle.DrawCircle
import com.extensions.getCompactColor

class SonarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseCanvasView(context, attrs) {

    companion object {
        const val MAX_ALPHA = 255
    }

    private val drawCircle = DrawCircle()

    private lateinit var animator: ValueAnimator

    private var currentRadius = 1f

    val paint = contentPaint.apply {
        style = Paint.Style.FILL
        color = context.getCompactColor(R.color.blue)
    }

    init {
        createSonar()
    }

    override fun onDraw(canvas: Canvas?) {
        val delta = drawCircle.getDefaultRadius(contentRect) / MAX_ALPHA
        paint.alpha = MAX_ALPHA - (delta * currentRadius).toInt()
        (1..3).forEach {
            drawCircle.drawCircle(canvas!!, paint, contentRect, ((delta * currentRadius) / 3) * it)
        }

        super.onDraw(canvas)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator.cancel()
    }

    private fun createSonar() {
        animator = ValueAnimator.ofFloat(1f, MAX_ALPHA.toFloat()).apply {
            repeatCount = ValueAnimator.INFINITE
            duration = 2000
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                currentRadius = it.animatedValue as Float
                invalidate()
            }
        }
    }
}