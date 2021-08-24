package com.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import com.extensions.px

class TicketView constructor(
    context: Context,
    attributeSet: AttributeSet?
) : BaseCanvasView(context, attributeSet) {

    val path = Path()

    private val dividerPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        //strokeCap = Paint.Cap.ROUND
    }

    private val strokePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    private var ticketCornerHeight: Float = 0f

    private var scallopPercent = 0f

    private var scallopCornerHeight = 0f

    private var scallopPosition = 0f

    private var dashWidth = 0f

    private var dashGap = 0f

    init {
        attributeSet?.apply {
            val typedArray = context.obtainStyledAttributes(this, R.styleable.TicketView)

            ticketColor(typedArray.getColor(R.styleable.TicketView_tct_color, Color.WHITE))

            ticketCornerHeight(
                typedArray.getDimension(
                    R.styleable.TicketView_tct_corner_radius,
                    8.px.toFloat()
                )
            )

            ticketScallopHeight(
                typedArray.getDimension(
                    R.styleable.TicketView_tct_scallop_height,
                    8.px.toFloat()
                )
            )

            ticketScallopPercent(
                typedArray.getFloat(
                    R.styleable.TicketView_tct_scallop_percent,
                    70f
                )
            )

            ticketStrokeColor(
                typedArray.getColor(
                    R.styleable.TicketView_tct_stroke_color,
                    Color.TRANSPARENT
                )
            )

            ticketStrokeWidth(typedArray.getDimension(R.styleable.TicketView_tct_stroke_width, 0f))

            ticketDividerColor(
                typedArray.getColor(
                    R.styleable.TicketView_tct_divider_color,
                    Color.LTGRAY
                )
            )

            ticketDividerWidth(
                typedArray.getDimension(
                    R.styleable.TicketView_tct_divider_width,
                    0f
                )
            )

            ticketDividerDashWidth(
                typedArray.getDimension(
                    R.styleable.TicketView_tct_divider_dash_width,
                    8.px.toFloat()
                )
            )

            ticketDividerGapWidth(
                typedArray.getDimension(
                    R.styleable.TicketView_tct_divider_dash_gap,
                    20.px.toFloat()
                )
            )

            typedArray.recycle()
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawTicketView(it)
            drawDivider(it)
        }
    }

    private fun ticketColor(color: Int) {
        contentPaint.color = color
    }

    private fun ticketCornerHeight(cornerRadius: Float) {
        ticketCornerHeight = cornerRadius * 2
    }

    private fun ticketScallopHeight(width: Float) {
        scallopCornerHeight = width
    }

    private fun ticketScallopPercent(percent: Float) {
        scallopPercent = percent / 100f
    }

    private fun ticketStrokeColor(color: Int) {
        strokePaint.color = color
    }

    private fun ticketStrokeWidth(width: Float) {
        strokePaint.strokeWidth = width
    }

    private fun ticketDividerColor(color: Int) {
        dividerPaint.color = color
    }

    private fun ticketDividerWidth(width: Float) {
        dividerPaint.strokeWidth = width
    }

    private fun ticketDividerDashWidth(width: Float) {
        dashWidth = width
    }

    private fun ticketDividerGapWidth(width: Float) {
        dashGap = width
    }

    private fun drawTicketView(canvas: Canvas) {
        Log.i(
            "TAG",
            "Percet: ${contentRect.bottom / 1.5f} - ${scallopPercent * contentRect.bottom}"
        )

        scallopPosition = scallopPercent * contentRect.bottom

        val rectRoundTopLeft = getRectTopLeft()

        path.arcTo(rectRoundTopLeft, 270f, -90f)

        val rectScallopLeft = getRectScallopLeft()

        path.arcTo(rectScallopLeft, 270f, 180f)

        val rectRoundBottomLeft = getRectBottomLeft()

        path.arcTo(rectRoundBottomLeft, 180f, -90f)

        val rectRoundBottomRight = getRectBottomRight()

        path.arcTo(rectRoundBottomRight, 90f, -90f)

        val rectScallopRight = getRectScallopRight()

        path.arcTo(rectScallopRight, 90f, 180f)

        val rectRoundTopRight = getRectTopRight()

        path.arcTo(rectRoundTopRight, 0f, -90f)

        path.close()

        canvas.drawPath(path, contentPaint)

        if (strokePaint.strokeWidth > 0) {
            canvas.drawPath(path, strokePaint)
        }
    }

    private fun getRectScallopLeft() = RectF(
        contentRect.left - scallopCornerHeight,
        scallopPosition - scallopCornerHeight,
        contentRect.left + scallopCornerHeight,
        scallopPosition + scallopCornerHeight
    )

    private fun getRectScallopRight() = RectF(
        contentRect.right - scallopCornerHeight,
        scallopPosition - scallopCornerHeight,
        contentRect.right + scallopCornerHeight,
        scallopPosition + scallopCornerHeight
    )

    private fun getRectTopLeft() = RectF(
        contentRect.left,
        contentRect.top,
        contentRect.left + ticketCornerHeight,
        contentRect.top + ticketCornerHeight
    )

    private fun getRectTopRight() = RectF(
        contentRect.right - ticketCornerHeight,
        contentRect.top,
        contentRect.right,
        contentRect.top + ticketCornerHeight
    )

    private fun getRectBottomLeft() = RectF(
        contentRect.left,
        contentRect.bottom - ticketCornerHeight,
        contentRect.left + ticketCornerHeight,
        contentRect.bottom
    )

    private fun getRectBottomRight() = RectF(
        contentRect.right - ticketCornerHeight,
        contentRect.bottom - ticketCornerHeight,
        contentRect.right,
        contentRect.bottom
    )

    private fun drawDivider(canvas: Canvas) {
        dividerPaint.pathEffect = DashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)

        canvas.drawLine(
            contentRect.left + scallopCornerHeight + strokePaint.strokeWidth,
            scallopPosition,
            contentRect.right - scallopCornerHeight - strokePaint.strokeWidth,
            scallopPosition,
            dividerPaint
        )
    }
}