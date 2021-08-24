package com.canvas.arc

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import com.canvas.BaseCanvasView
import com.extensions.RectFFactory
import kotlin.math.cos
import kotlin.math.sin

class ArcView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseCanvasView(context, attrs) {

    private val linePaint = Paint().apply {
        isAntiAlias = true
    }

    private var mAngle = 0f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        contentPaint.color = Color.GRAY
        canvas?.drawArc(getBounds(), 0f, 360f, true, contentPaint)

        val cx = getBounds().centerX()
        val cy = getBounds().centerY()
        val angle = Math.toRadians(mAngle.toDouble())
        val radius = getBounds().width() / 2

        contentPaint.color = Color.BLUE
        contentPaint.style = Paint.Style.STROKE

        canvas?.drawRect(getBounds(), contentPaint)

        canvas?.drawLine(cx, getBounds().top, cx, getBounds().bottom, contentPaint)

        canvas?.drawLine(getBounds().left, cy, getBounds().right, cy, contentPaint)

        val xPos = (cx + radius * sin(angle)).toFloat()
        val yPos = (cy - radius * cos(angle)).toFloat()

        Log.i(
            "TAG", "cx: $cx - cy: $cy - radius: $radius - mAngle: $mAngle - angle: $angle" +
              " - sin: ${sin(angle)} - cos: ${cos(angle)}" +
              " - xPos: $xPos - yPos: $yPos"
        )

        canvas?.drawCircle(xPos, yPos, 30f, contentPaint)

        linePaint.textSize = 30f
        linePaint.textAlign = Paint.Align.CENTER

        canvas?.drawText(
            String.format(
                "%.0f",
                Math.toDegrees(angle)
            ), xPos, yPos, linePaint
        )

        canvas?.drawLine(xPos, yPos, cx, cy, linePaint)
    }

    private fun getBounds(): RectF {
        return RectFFactory.fromCenter(
            PointF(contentRect.right / 2, contentRect.bottom / 2),
            getSize(),
            getSize()
        )
    }

    private fun getSize(): Float {
        return (if (contentRect.bottom > contentRect.right)
            contentRect.right
        else
            contentRect.bottom)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimation()
    }

    fun startAnimation() {
        val anim = ObjectAnimator.ofFloat(this, "angle", 0f, 360f)
        anim.duration = 3000
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.addUpdateListener { invalidate() }
        // anim.repeatCount = ObjectAnimator.INFINITE
        anim.start()
    }

    fun setAngle(angle: Float) {
        mAngle = angle
        invalidate()
    }
}