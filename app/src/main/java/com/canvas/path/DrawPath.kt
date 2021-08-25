package com.canvas.path

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.canvas.R
import com.extensions.getCompactColor

class DrawPath @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val linePaint = Paint().apply {
        this.color = context.getCompactColor(R.color.blue)
        this.style = Paint.Style.STROKE
        this.strokeWidth = 20f
        this.isAntiAlias = true
    }

    private val rectPaint = Paint().apply {
        this.color = context.getCompactColor(R.color.red)
        this.style = Paint.Style.STROKE
        this.strokeWidth = 20f
    }

    private var linePath = Path()

    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private val rectF = RectF()
    private val padding = 100f

    private lateinit var regionToCheck: Region

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        centerX = width.toFloat() / 2
        centerY = height.toFloat() / 5
        /*
        linePath = createCubicToCurvedPath()
        canvas?.drawPath(linePath, linePaint)

        linePath.reset()
        centerY = height / 4f
        linePath = createQuadToCurvedPath()
        canvas?.drawPath(linePath, linePaint)

        linePath.reset()
        centerY = height / 2.1f
        linePath = createLinePath()
        canvas?.drawPath(linePath, linePaint)

        linePath.reset()
        centerY = height / 1.8f
        linePath = createLineArc(canvas!!)

        linePath.reset()

         */
       // centerY = height / 1.5f
        //linePath = curveBottomNavigation()

        canvas?.drawPath(linePath, linePaint)
    }

    private fun createQuadToCurvedPath(): Path {
        val lineSize = width - (padding * 2) //width.toFloat() - (paddingLeft + paddingRight)
        val curve = 600

        linePath.moveTo(centerX - lineSize / 2, centerY)
        linePath.quadTo(centerX, centerY + curve, centerX + lineSize / 2, centerY)

        return linePath
    }

    private fun createCubicToCurvedPath(): Path {
        val lineSize = width - (padding * 2) //width.toFloat() - (paddingLeft + paddingRight)
        val halfLine = lineSize / 2
        val middleHalLine = halfLine / 2

        linePath.moveTo(centerX - halfLine, centerY)
        linePath.cubicTo(
            centerX - middleHalLine,
            centerY - halfLine,
            centerX + middleHalLine,
            centerY + halfLine,
            centerX + halfLine,
            centerY
        )

        return linePath
    }

    private fun createLinePath(): Path {
        val lineSize = width - (padding * 2) //width.toFloat() - (paddingLeft + paddingRight)
        val halfLine = lineSize / 2
        linePath.moveTo(centerX - halfLine, centerY)
        linePath.rLineTo(lineSize, 0f)
        linePath.rLineTo(0f, 100f)
        linePath.lineTo(halfLine + (centerX - halfLine) + 30f, centerY + 100f)
        linePath.rLineTo(-30f, 40f)
        linePath.rLineTo(-30f, -40f)
        linePath.lineTo(centerX - halfLine, centerY + 100)
        linePath.close()
        return linePath
    }

    private fun createLineArc(canvas: Canvas): Path {
        val lineSize = width - (padding * 2) //width.toFloat() - (paddingLeft + paddingRight)
        val halfLine = lineSize / 2
        linePath.moveTo(centerX - halfLine, centerY)
        linePath.rLineTo(centerX + 20f, 230f)

        rectF.set(
            (centerX * 2 - halfLine),
            centerY + 200,
            lineSize,
            centerY + 450f
        )

        // canvas.drawRect(rectF, rectPaint)

        linePath.arcTo(rectF, 225f, 330f)

        linePath.rLineTo(-centerX, -230f)

        linePath.close()

        return linePath
    }

    private fun curveBottomNavigation(): Path {
        val fabSize = 140f
        val bottomSize = 180f
        val lineSize = width.toFloat()

        val leftPoint = ((lineSize / 2) - fabSize / 2) - (padding / 2)
        val rightPoint = ((lineSize / 2) + fabSize)

        linePath.moveTo(padding, centerY)
        linePath.lineTo(leftPoint, centerY)

        rectF.set(
            leftPoint,
            centerY - (fabSize),
            rightPoint,
            centerY + (fabSize)
        )
        // canvas.drawRect(rectF, rectPaint)

        linePath.arcTo(rectF, 180f, -180f)

        linePath.lineTo(lineSize - padding, centerY)

        linePath.rLineTo(0f, bottomSize)
        linePath.lineTo(padding, centerY + bottomSize)

        linePath.close()

        return linePath
    }

    private fun createTouchRegion(){
        val pathBounds = RectF()
        linePath.computeBounds(pathBounds, true)
        regionToCheck = Region(
            pathBounds.left.toInt(),
            pathBounds.top.toInt(),
            pathBounds.right.toInt(),
            pathBounds.bottom.toInt()
        )
        regionToCheck.setPath(linePath, regionToCheck)
    }

    private fun isTouchRegion(x: Int?, y: Int?): Boolean{
       return x?.let { regionToCheck.contains(it, y!!)} ?: false
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        linePath = curveBottomNavigation()
        createTouchRegion()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result = false
        val pointX = event?.x?.toInt()
        val pointY = event?.y?.toInt()

        if (isTouchRegion(pointX, pointY)) {

            Log.i("Canvas", "Point $x - $y")

            result = super.onTouchEvent(event)
        }
        return  result
    }

}