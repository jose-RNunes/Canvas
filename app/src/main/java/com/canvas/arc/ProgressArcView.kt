package com.canvas.arc

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import com.canvas.BaseCanvasView
import com.canvas.R
import com.extensions.RectFFactory
import kotlin.math.min


class ProgressArcView(
    context: Context,
    attrs: AttributeSet? = null
) : BaseCanvasView(context, attrs) {

    private var mCircleRect: RectF? = null
    private var mProgressPaint: Paint? = null
    private var mBgPaint: Paint? = null
    private val mCirclePadding = 100f
    private var progress = 0
    private var mProgressColor = 0
    private val DEFAULT_COLOR: Int = Color.RED

    private var mBgColor = 0
    private val DEFAULT_BG_COLOR: Int = Color.GRAY

    private var mStrokeWidth = 0f
    private val DEFAULT_STROKE_WIDTH = 10f

    init {
        attrs?.apply {
            val typedArray = context.obtainStyledAttributes(this, R.styleable.ArchChartView)
            mProgressColor =
                typedArray.getColor(R.styleable.ArchChartView_ac_progress_color, DEFAULT_COLOR);
            mBgColor = typedArray.getColor(R.styleable.ArchChartView_ac_bg_color, DEFAULT_BG_COLOR);
            mStrokeWidth = typedArray.getDimension(
                R.styleable.ArchChartView_ac_stroke_width,
                    DEFAULT_STROKE_WIDTH
                )
            setProgress(typedArray.getInt(R.styleable.ArchChartView_ac_progress, 50));
            init()
            typedArray.recycle()
        }
    }

    fun setProgress(progress: Int) {
        this.progress = (progress * 3.6).toInt()
    }

    fun startAnimation(endProgress: Int) {
        val anim = ObjectAnimator.ofInt(this, "progress", 0, endProgress)
        anim.duration = 4000
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.addUpdateListener { invalidate() }
        anim.repeatCount = ObjectAnimator.INFINITE
        anim.start()
    }


    private fun init() { // bg
        mBgPaint = safeBasePaint()

        mProgressPaint = safeProgressPaint()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.let { drawChart(canvas) }
    }

    private fun drawChart(canvas: Canvas) {
        canvas.drawArc(getBounds(), 0.toFloat(), 360.toFloat(), false, safeBasePaint())
        canvas.drawArc(getBounds(), 270.toFloat(), -progress.toFloat(), false, safeProgressPaint())
    }

    private fun safeBasePaint() = mBgPaint ?: Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = mBgColor
        style = Paint.Style.STROKE
        strokeWidth = mStrokeWidth
        strokeCap = Paint.Cap.ROUND
    }

    private fun safeProgressPaint() = mProgressPaint ?: Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = mProgressColor
        style = Paint.Style.STROKE
        strokeWidth = mStrokeWidth + 10f
        strokeCap = Paint.Cap.ROUND
        shader = SweepGradient(0f, 0f, intArrayOf(Color.CYAN, Color.MAGENTA, Color.YELLOW), null)
    }

    private fun getBounds(): RectF {
        return RectFFactory.fromCircle(
            PointF(contentRect.centerX(), contentRect.centerY()),
            getRadius()
        )
    }

    private fun getRadius() = min(contentRect.right, contentRect.bottom) / 2
}