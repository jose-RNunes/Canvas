package com.canvas.arc

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.canvas.BaseCanvasView
import com.extensions.RectFFactory
import kotlin.math.min


class StepArcView(
    context: Context,
    attrs: AttributeSet? = null
) : BaseCanvasView(context, attrs) {

    private var mPaint: Paint? = null
    private var mSections = 0
    private var mFullArcSliceLength = 0
    private var mColorArcLineLength = 0
    private var mArcSectionGap = 0


    init {
        init()
    }

    private fun init() {
        mPaint = Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.STROKE
            strokeWidth = 20f
            color = Color.GRAY
            strokeCap = Paint.Cap.ROUND
        }

        mSections = 8
        mFullArcSliceLength = 360 / mSections
        mArcSectionGap = mFullArcSliceLength / 10
        mColorArcLineLength = mFullArcSliceLength - 2 * mArcSectionGap
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until mSections) {
            canvas.drawArc(
                getBounds(),
                (i * mFullArcSliceLength + mArcSectionGap).toFloat(),
                mColorArcLineLength.toFloat(),
                false,
                mPaint!!
            )
        }
    }

    private fun getBounds(): RectF {
        return RectFFactory.fromCircle(
            PointF(contentRect.centerX(), contentRect.centerY()),
            getRadius()
        )
    }

    private fun getRadius() = min(contentRect.right, contentRect.bottom) / 2
}