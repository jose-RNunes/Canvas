package com.canvas.path

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import com.canvas.BaseCanvasView
import com.canvas.R
import com.extensions.getCompactColor
import com.extensions.px

class DrawTriangleProgress @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
) : BaseCanvasView(context, attrs) {

    private val trianglePaint = contentPaint.apply {
        isAntiAlias = true
        strokeWidth = 10.px.toFloat()
        color = context.getCompactColor(R.color.grey)
        style = Paint.Style.STROKE
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getCompactColor(R.color.green)
        style = Paint.Style.FILL
    }

    private val path = Path()

    private var progress: Float = trianglePaint.strokeWidth

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val path = drawTriangle()
        canvas.clipPath(path)
        canvas.drawPath(path, trianglePaint)
        drawProgress(canvas)
    }

    private fun drawTriangle(): Path {
        val middleY = contentRect.bottom / 2f
        path.moveTo(contentRect.right, contentRect.top)
        path.rLineTo(0f, middleY)
        path.lineTo(contentRect.left, middleY)
        path.close()
        return path
    }

    private fun drawProgress(canvas: Canvas) {
        val rectF = RectF(
            contentRect.left,
            contentRect.top,
            progress,
            contentRect.bottom
        )
        canvas.drawRect(rectF, progressPaint)
    }

    private fun setProgress(progress: Float) {
        this.progress = progress
        Log.i("TAG", "Progress: $progress")
    }

    fun startAnimation() {
        val anim = ObjectAnimator.ofFloat(
            this,
            "progress",
            0f,
            contentRect.left + contentRect.right
        )
        anim.duration = 3000
        anim.addUpdateListener {
            invalidate()
        }
        anim.start()
    }

}