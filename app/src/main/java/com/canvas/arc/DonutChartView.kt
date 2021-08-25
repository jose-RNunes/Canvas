package com.canvas.arc

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.RequiresApi
import com.canvas.BaseCanvasView
import com.canvas.R
import com.extensions.RectFFactory
import com.extensions.getCompactColor
import com.extensions.px
import kotlin.math.cos
import kotlin.math.sin

class DonutChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BaseCanvasView(context, attrs) {

    private val donutsItem = mutableListOf(
        DonutItem(90.0, "Comedia", R.color.yellow),
        DonutItem(100.0, "Ação", R.color.orange),
        DonutItem(50.0, "Romance", R.color.blue),
        DonutItem(40.0, "Drama", R.color.red),
        DonutItem(20.0, "Ciência", R.color.green)
    )

    private val linePaint = Paint().apply {
        strokeWidth = 2.px.toFloat()
        color = Color.WHITE
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val circlePaint = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
    }

    private val textLabelPaint = Paint().apply {
        textSize = 16.px.toFloat()
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            createArcs(canvas)
            drawMiddleCircle(canvas)
        }
    }

    fun setDonutsValue(donutsItem: List<DonutItem>) {
        this.donutsItem.clear()
        this.donutsItem.addAll(donutsItem)
        invalidate()
    }

    private fun createArcs(canvas: Canvas) {
        var startAngle = -90f
        this.donutsItem.forEach {
            contentPaint.color = context.getCompactColor(it.color)
            val sweepAngle = (it.value / getTotal()) * 360
            Log.i("TAG", "Angles: $startAngle - $sweepAngle")
            canvas.drawArc(getBounds(), startAngle, sweepAngle.toFloat(), true, contentPaint)
            startAngle += sweepAngle.toFloat()

            drawDiver(canvas, startAngle + 90.0)
            drawLabel(canvas, it.label, startAngle + 90.0, sweepAngle)
        }
    }

    private fun drawDiver(canvas: Canvas, startAngle: Double) {
        val cx = getBounds().centerX()
        val cy = getBounds().centerY()
        val angle = Math.toRadians(startAngle)

        val degress = Math.toDegrees(angle)

        Log.i("TAG", "Math Angle: $angle - degress: $degress")

        val stopX = (cx + (getRadius() - getRadius() / 2) * sin(angle)).toFloat()
        val stopY = (cy - (getRadius() - getRadius() / 2) * cos(angle)).toFloat()

        canvas.drawLine(cx, cy, stopX, stopY, linePaint)
    }

    private fun drawLabel(canvas: Canvas, label: String, startAngle: Double, sweepAngle: Double) {
        val cx = getBounds().centerX()
        val cy = getBounds().centerY()
        val angle = Math.toRadians(startAngle)
        val sweep = Math.toRadians(sweepAngle)


        val textBounds = Rect()

        textLabelPaint.getTextBounds(label, 0, label.length, textBounds)

        val x = (cx + (getRadius() * 0.4) * sin(angle - sweep / 2)).toFloat()
        val y = (cy - (getRadius() * 0.4) * cos(angle - sweep / 2)).toFloat()

        canvas.drawText(label, x, y, textLabelPaint)
    }

    private fun drawMiddleCircle(canvas: Canvas) {
        canvas.drawCircle(
            getBounds().centerX(),
            getBounds().centerY(),
            getRadius() * 0.3f,
            circlePaint
        )
        drawCenterText(canvas)
    }

    private fun drawCenterText(canvas: Canvas) {
        val text = "Gêneros de Filmes Favoritos"
        val textPaint = TextPaint().apply {
            color = Color.GRAY
            textSize = 22.px.toFloat()
        }

        val staticLayout = getStaticLayout(
            text,
            (getRadius() * 0.3).toInt(),
            textPaint
        )

        canvas.save()
        canvas.translate(
            getBounds().centerX() - staticLayout.width / 2,
            getBounds().centerY() - staticLayout.height / 2
        )
        staticLayout.draw(canvas)
        canvas.restore()
    }

    private fun getBounds(): RectF {
        return RectFFactory.fromCenter(
            PointF(contentRect.right / 2, contentRect.bottom / 2),
            getRadius(),
            getRadius()
        )
    }

    private fun getRadius(): Float {
        return (if (contentRect.bottom > contentRect.right)
            contentRect.right
        else
            contentRect.bottom)
    }

    private fun getStaticLayout(
        text: String,
        width: Int,
        textPaint: TextPaint,
        textAlignment: Layout.Alignment = Layout.Alignment.ALIGN_CENTER
    ): StaticLayout {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            StaticLayout.Builder
                .obtain(text, 0, text.length, textPaint, width)
                .setAlignment(textAlignment)
                .build()
        } else {
            return StaticLayout(
                text,
                0,
                text.length,
                textPaint,
                width,
                textAlignment,
                1f,
                1f,
                false
            )
        }
    }

    private fun getTotal(): Double {
        return donutsItem.map { it.value }.reduce { acc, d -> acc + d }
    }
}
