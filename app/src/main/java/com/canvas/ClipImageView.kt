package com.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class ClipImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) :
    AppCompatImageView(context, attrs) {

    val path = Path()

    init {
        this.setImageResource(R.drawable.download)
        this.scaleType = ScaleType.FIT_XY
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.clipPath(path)
        super.onDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        drawPath()
    }

    private fun drawPath(): Path {
        val maxHeight = height.toFloat() - (height / 6)
        path.moveTo(0f, 0f)
        path.lineTo(0f, maxHeight)
        path.quadTo(
            width.toFloat() / 2,
            height.toFloat(),
            width.toFloat(),
            maxHeight
        )
        path.lineTo(width.toFloat(), 0f)
        path.close()
        return path
    }

}