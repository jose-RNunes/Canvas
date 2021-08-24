package com.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View


class MyStrokeCircleView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var mPaint: Paint? = null
    private var mRect: RectF? = null
    private var mPadding = 100f
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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
       mRect = RectF(
            0 + mPadding,
            0 + mPadding,
            width - mPadding,
            height - mPadding
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until mSections) {
            canvas.drawArc(
                mRect!!,
                (i * mFullArcSliceLength + mArcSectionGap).toFloat(),
                mColorArcLineLength.toFloat(),
                false,
                mPaint!!
            )
        }
    }
}