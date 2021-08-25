package com.canvas

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.plus
import com.extensions.getCompactColor
import com.extensions.px
import kotlin.math.max

class DrawGraph @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private companion object {
        var STROKE_WIDTH_DP = 3.px.toFloat()
        var PADDING = 20.px
        var RADIUS = 5.px.toFloat()
        var TEXT_SIZE = 10.px
    }

    private val dataGraph = listOf(
        200,
        800,
        300,
        500,
        300
    )

    private val points = mutableListOf<PointF>()

    private val conPoint1 = mutableListOf<PointF>()
    private val conPoint2 = mutableListOf<PointF>()

    private val graphPaint = Paint().apply {
        this.style = Paint.Style.STROKE
        this.strokeWidth = STROKE_WIDTH_DP
        this.isAntiAlias = true
    }

    private val dotPaint = Paint().apply {
        this.style = Paint.Style.FILL
        this.isAntiAlias = true
        this.color = Color.BLUE
    }

    private val textPaint = Paint().apply {
        this.textSize = TEXT_SIZE.toFloat()
        this.isAntiAlias = true
        this.color = Color.BLUE
    }

    private val gradientColors = intArrayOf(
        context.getCompactColor(R.color.colorStart),
        context.getCompactColor(R.color.colorEnd)
    )

    private val gradientPaint = Paint().apply {
        isAntiAlias = true
        shader = LinearGradient(
            0f,
            paddingTop.toFloat(),
            0f,
            60.px.toFloat(),
            gradientColors,
            null,
            Shader.TileMode.CLAMP
        )
    }

    private val path: Path = Path()

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        initPoints()
        canvas?.let {
            drawDots(canvas)
            //  drawLine(canvas)
            drawBezier(canvas)

            drawGradientBezier(canvas)
        }
    }

    private fun initPoints() {
        val maxValue = dataGraph.max()?.toFloat() ?: 0f
        val sectionWidth = width / (dataGraph.size - 1)

        for (i in dataGraph.indices) {
            val x = i * sectionWidth
            val y = (height - (height * (dataGraph[i] / maxValue)))
            points.add(PointF(x.toFloat(), y.takeIf { it > 0 } ?: 60.px.toFloat()))
        }
    }

    private fun drawDots(canvas: Canvas) {
        points.forEachIndexed { index, pointF ->
            canvas.drawCircle(pointF.x, pointF.y, RADIUS, dotPaint)
            canvas.drawText(
                dataGraph[index].toString(),
                pointF.x + 20.px,
                pointF.y - 20.px,
                textPaint
            )
        }
    }

    private fun drawLine(canvas: Canvas) {
        path.reset()
        path.moveTo(paddingLeft.toFloat(), 450f)
        points.forEach {
            path.lineTo(it.x, it.y)
        }

        path.lineTo(width.toFloat(), points.last().y / 2)

        canvas.drawPath(path, graphPaint)
    }

    private fun drawBezier(canvas: Canvas) {

        for (i in points.indices) {
            Log.i("BASE POINTS", "${points[i].x} -- ${points[i].y}")
        }

        for (i in 1 until points.size) {
            conPoint1.add(PointF((points[i].x + points[i - 1].x) / 2, points[i - 1].y))
            conPoint2.add(PointF((points[i].x + points[i - 1].x) / 2, points[i].y))

            Log.i("POINTS 1",
                "Point X: ${(points[i].x + points[i - 1].x) / 2} -- Point Y:  ${points[i - 1].y}"
            )

            Log.i("POINTS 2",
                "Point X: ${(points[i].x + points[i - 1].x) / 2} -- Point Y:  ${points[i].y}"
            )
        }

        conPoint1.forEach {
            dotPaint.color = Color.RED
            canvas.drawCircle(it.x, it.y, RADIUS, dotPaint)
        }

        conPoint2.forEach {
            canvas.drawCircle(it.x, it.y, RADIUS, dotPaint)
        }

        path.reset()
        path.moveTo(points.first().x, points.first().y)

        for (i in 1 until points.size) {
            path.cubicTo(
                conPoint1[i - 1].x,
                conPoint1[i - 1].y,
                conPoint2[i - 1].x,
                conPoint2[i - 1].y,
                points[i].x,
                points[i].y
            )

            Log.i("CUBIC POINTS",

                "index: ${i - 1} -> Point 1 X: ${conPoint1[i - 1].x} -- Point 1 Y: ${conPoint1[i - 1].y}\n " +
                    "index: ${i - 1} -> Point 2 X: ${conPoint2[i - 1].x} -- Point 2 Y: ${conPoint2[i - 1].y}\n " +
                    "index $i -> Point End X: ${points[i].x} -- Point End Y ${points[i].y}"
            )
        }
        canvas.drawPath(path, graphPaint)
    }

    private fun drawGradientBezier(canvas: Canvas) {
        for (i in 1 until points.size) {
            conPoint1.add(PointF((points[i].x + points[i - 1].x) / 2, points[i - 1].y))
            conPoint2.add(PointF((points[i].x + points[i - 1].x) / 2, points[i].y))
        }

        path.reset()
        path.moveTo(points.first().x, points.first().y)

        for (i in 1 until points.size) {
            path.cubicTo(
                conPoint1[i - 1].x,
                conPoint1[i - 1].y,
                conPoint2[i - 1].x,
                conPoint2[i - 1].y,
                points[i].x,
                points[i].y
            )
        }

       val maxY =  points.maxBy {
           it.y
       }?.y

        path.lineTo(width.toFloat(), maxY?.plus(100f) ?: height.toFloat())
        path.lineTo(0f, maxY?.plus(100f) ?: height.toFloat())

        canvas.drawPath(path, gradientPaint)
    }
}