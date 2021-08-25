package com.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.extensions.dpToPx
import com.extensions.getCompactColor
import com.extensions.getDimen

class ProgressLine @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private companion object {
        const val PROGRESS_START = 0
        const val PROGRESS_END = 1
    }

    private val progressPadding = context.dpToPx(16f)

    private lateinit var backgroundPaint: Paint

    private lateinit var progressPaint: Paint

    private var progress = 0

    private val minProgressWidth = context.getDimen(R.dimen.dcl_progress_min_width).toInt()

    private val minProgressHeight = context.getDimen(R.dimen.dcl_progress_min_height).toInt()

    private var progressType: Int = PROGRESS_START

    private var baseColor: Int = 0

    private var progressColor: Int = 0

    private var progressStroke: Float = 0.0f

    private var progressBackgroundStroke: Float = 0.0f

    init {
        attrs?.apply {
            val typedArray = context.obtainStyledAttributes(this, R.styleable.ProgressLine)

            progressType =
                typedArray.getInt(R.styleable.ProgressLine_dlc_progress_type, PROGRESS_START)

            baseColor = typedArray.getColor(
                R.styleable.ProgressLine_dlc_color,
                context.getCompactColor(R.color.grey)
            )

            progressColor = typedArray.getColor(
                R.styleable.ProgressLine_dlc_progress_color,
                context.getCompactColor(R.color.blue)
            )

            progressBackgroundStroke = typedArray.getDimension(
                R.styleable.ProgressLine_dlc_progress_background_stroke,
                context.getDimen(R.dimen.dlc_progress_background_stroke)
            )

            progressStroke = typedArray.getDimension(
                R.styleable.ProgressLine_dlc_progress_stroke,
                context.getDimen(R.dimen.dlc_progress_stroke)
            )

            setProgress(typedArray.getInt(R.styleable.ProgressLine_dlc_progress, 50))

            init()

            typedArray.recycle()
        }
    }

    private fun init() {
        backgroundPaint = Paint().apply {
            this.color = baseColor
            this.strokeWidth = progressBackgroundStroke
            this.isAntiAlias = true
            this.strokeCap = Paint.Cap.ROUND
        }

        progressPaint = Paint().apply {
            this.color = progressColor
            this.strokeWidth = progressStroke
            this.isAntiAlias = true
            this.strokeCap = Paint.Cap.ROUND
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            it.drawLine(
                progressPadding,
                height.toFloat() / 2,
                width.toFloat() - progressPadding,
                height.toFloat() / 2,
                backgroundPaint
            )

            drawProgress(it)
        }
    }

    private fun drawProgressStart(canvas: Canvas) {
        canvas.drawLine(
            progressPadding,
            height.toFloat() / 2,
            if(calcProgress() > progressPadding) calcProgress() else progressPadding,
            height.toFloat() / 2,
            progressPaint
        )
    }

    private fun drawProgressEnd(canvas: Canvas) {
        canvas.drawLine(
            calcProgress(),
            height.toFloat() / 2,
            width - progressPadding,
            height.toFloat() / 2,
            progressPaint
        )
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> minProgressWidth
            MeasureSpec.UNSPECIFIED -> minProgressWidth
            else -> minProgressWidth
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> minProgressHeight
            MeasureSpec.UNSPECIFIED -> minProgressHeight
            else -> minProgressHeight
        }

        setMeasuredDimension(width, height)
    }

    private fun calcProgress(): Float  {
       return if(progressType == PROGRESS_END && progress == 0){
            progressPadding
        }else{
            (this.progress.toFloat() / 100) * (width - progressPadding)
        }
    }

    private fun drawProgress(canvas: Canvas){
        when{
            progress > 0 && progressType == PROGRESS_START -> drawProgressStart(canvas)
            progressType == PROGRESS_END -> drawProgressEnd(canvas)
            else -> return
        }
    }

}
