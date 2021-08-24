package com.canvas

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import com.extensions.getCompactColor
import com.extensions.px

class DrawShadow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getCompactColor(R.color.grey)
        strokeWidth = 10f
        isAntiAlias = true
    }

    val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getCompactColor(R.color.blue)
        isAntiAlias = true
        setShadowLayer(20f, -2f, -2f, Color.GRAY);
    }

    val path = Path()
    val rect = RectF()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {

        rect.set(
            30f,
            30f,
            width.toFloat() - 30f,
            height.toFloat() - 30f
        )
/*
            it.drawRoundRect(
                rect,
                8.px.toFloat(),
                8.px.toFloat(),
                borderPaint
            )

            rect.inset(6.px.toFloat(), 6.px.toFloat())

            canvas.drawRoundRect(
                rect,
                8.px.toFloat(),
                8.px.toFloat(),
                fillPaint
            )

*/
            canvas.drawPath(drawPath(), fillPaint)
        }
    }

    private fun drawPath(): Path {
        path.moveTo(10f, 10f)
        path.lineTo(10f, height - 60.px.toFloat())
        path.quadTo(width.toFloat() / 2, height.toFloat(), width.toFloat() - 10f, height - 60.px.toFloat())
        path.lineTo(width.toFloat() - 10f, 10f)
        path.close()
        return path
    }

    override fun getOutlineProvider(): ViewOutlineProvider {
        return object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setConvexPath(path)
            }
        }
    }
}