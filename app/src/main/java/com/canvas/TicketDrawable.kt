package com.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.extensions.px

class TicketDrawable @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val contentRect = RectF()

    private val path = Path()

    private val ticketCornerHeight = 16.px

    private val scallopCornerHeight = 16.px

    private var scallopPosition = 0f

    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.LTGRAY
        style = Paint.Style.STROKE
        strokeWidth = 2.px.toFloat()
    }

    override fun dispatchDraw(canvas: Canvas?) {
        scallopPosition = contentRect.centerY()
        path.arcTo(getRectTopLeft(), 270f, -90f)
        path.arcTo(getRectBottomLeft(), 180f, -90f)
        path.arcTo(getRectBottomRight(), 90f, -90f)
        path.arcTo(getRectScallopRight(), 90f, 180f)
        path.arcTo(getRectTopRight(), 0f, -90f)
        path.close()
        canvas?.drawPath(path, paint)
        super.dispatchDraw(canvas);
    }


    private fun getRectTopLeft() = RectF(
        contentRect.left.toFloat(),
        contentRect.top.toFloat(),
        contentRect.left.toFloat() + ticketCornerHeight,
        contentRect.top.toFloat() + ticketCornerHeight
    )

    private fun getRectTopRight() = RectF(
        contentRect.right - ticketCornerHeight,
        contentRect.top,
        contentRect.right,
        contentRect.top + ticketCornerHeight
    )

    private fun getRectBottomRight() = RectF(
        contentRect.right - ticketCornerHeight,
        contentRect.bottom - ticketCornerHeight,
        contentRect.right,
        contentRect.bottom
    )

    private fun getRectBottomLeft() = RectF(
        contentRect.left,
        contentRect.bottom - ticketCornerHeight,
        contentRect.left + ticketCornerHeight,
        contentRect.bottom
    )

    private fun getRectScallopRight() = RectF(
        contentRect.right - scallopCornerHeight,
        scallopPosition - scallopCornerHeight,
        contentRect.right + scallopCornerHeight,
        scallopPosition + scallopCornerHeight
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateContentRect()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        updateContentRect()
    }

    private fun updateContentRect() {
        contentRect.set(
            paddingStart.toFloat() + paint.strokeWidth,
            paddingTop.toFloat() + paint.strokeWidth,
            (width - paddingEnd).toFloat() - paint.strokeWidth,
            (height - paddingBottom).toFloat() - paint.strokeWidth
        )
    }
}