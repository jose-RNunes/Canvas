package com.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.extensions.getCompactColor
import com.extensions.px
import kotlinx.android.synthetic.main.view_alert_window.view.*


class CustomAlertWindowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    val view: ConstraintLayout = LayoutInflater.from(context)
        .inflate(R.layout.view_alert_window, this, true) as ConstraintLayout

    private val cornerRadius = 4.px.toFloat()

    private val rectF = RectF()

    private var mWidth: Float = 0f

    private var mHeight: Float = 0f

    private var backgroundColor: Int? = context.getCompactColor(R.color.green)


    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = 4.px.toFloat()
        color = context.getCompactColor(R.color.blue)
    }

    private val rectPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 4.px.toFloat()
        color = context.getCompactColor(R.color.red)
    }

    private val path = Path()

    init {
        setWillNotDraw(false)
    }

    fun setAlertBackgroundColor(backgroundColor: Int?) {
        backgroundColor?.let {
            this.backgroundColor = it
            this.paint.color = it
        }
    }

    fun setMessage(message: String?) {
        message?.let {
            this.tv_message.text = it
        }
    }

    fun setIcon(icon: Drawable?) {
        icon?.let {
            this.iv_icon.setImageDrawable(it)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // canvas?.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)

        val padding = 5.px.toFloat()
        val arcSize = 30.px.toFloat() + padding
        val middleArcSize = arcSize / 2

        path.moveTo(arcSize, padding)

        path.lineTo(width / 1.5f, padding)

        rectF.set(width / 1.5f, padding, width /1.5f + arcSize, arcSize)

        path.addArc(rectF, 270f, 90f)

        canvas?.drawRect(rectF, rectPaint)

        path.lineTo(width / 1.5f + arcSize, height / 1.5f)

        path.lineTo(width - padding, height - padding)

        path.rLineTo(arcSize, 0f)

        rectF.set(padding, height - arcSize, arcSize, height - padding)

        canvas?.drawRect(rectF, rectPaint)

        path.arcTo(rectF, 90f, 90f)

        path.lineTo(padding, arcSize)

        rectF.set(padding, padding, arcSize, arcSize)

        canvas?.drawRect(rectF, rectPaint)

        path.arcTo(rectF, 180f, 90f)

        canvas?.drawPath(path, paint)
    }

    private fun failt() {

        val padding = 5.px.toFloat()
        val arcSize = 30.px.toFloat()
        val middleArcSize = arcSize / 2

        path.moveTo(arcSize, padding)

        // First Arc
        rectF.set(padding, padding, arcSize, arcSize)

        path.addArc(rectF, 270f, (-90).toFloat())

        path.lineTo(padding, mHeight - middleArcSize)

        // Second Arc
        rectF.set(padding, mHeight - arcSize, arcSize, mHeight - padding)

        path.addArc(rectF, 180f, (-90).toFloat())

        path.lineTo(mWidth - middleArcSize, mHeight - padding)

        // Three Arc
        rectF.set(mWidth - arcSize, mHeight - arcSize, mWidth - padding, mHeight - padding)

        path.addArc(rectF, 90f, (-90).toFloat())

        path.lineTo(mWidth - padding, middleArcSize)

        // Fourth arc
        rectF.set(mWidth - arcSize, padding, mWidth - padding, arcSize)

        path.addArc(rectF, 0f, (-90).toFloat())

        path.lineTo(middleArcSize, padding)

        //canvas?.drawPath(path, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        //rectF.set(0.toFloat(), 0.toFloat(), mWidth, mHeight)
    }
}
