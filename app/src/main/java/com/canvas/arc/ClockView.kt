package com.canvas.arc

import android.animation.TimeAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RadialGradient
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.util.Log
import com.canvas.BaseCanvasView
import com.canvas.R
import com.extensions.RectFFactory
import com.extensions.getCompactColor
import com.extensions.px
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class ClockView(
    context: Context,
    attributeSet: AttributeSet?
) : BaseCanvasView(context, attributeSet) {

    private var second = 0

    private var minute = 0

    private var hour = 0

    private var timeAnimator: TimeAnimator? = null

    private val borderPaint = Paint().apply {
        isAntiAlias = true
        color = context.getCompactColor(R.color.white)
        strokeWidth = 10.px.toFloat()
        style = Paint.Style.STROKE
    }

    private val stepPaint = Paint().apply {
        isAntiAlias = true
        color = context.getCompactColor(R.color.white)
        strokeWidth = 2.px.toFloat()
        style = Paint.Style.STROKE
    }

    private val centerPaint = Paint().apply {
        isAntiAlias = true
        color = context.getCompactColor(R.color.white)
    }

    private val handsPaint = Paint().apply {
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 10.px.toFloat()
    }

    private val secondsPaint = Paint().apply {
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 5.px.toFloat()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        timeAnimator = TimeAnimator().apply {
            setTimeListener { animation, totalTime, deltaTime ->
                startDateTime()
            }
        }
        timeAnimator?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        timeAnimator?.cancel()
        timeAnimator?.removeAllListeners()
        timeAnimator?.setTimeListener(null)
        timeAnimator = null
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(context.getCompactColor(R.color.colorPrimary))
        canvas?.let {
            drawSteps(it)
            drawBackground(it)
            drawBorder(it)
            drawHourHand(it)
            drawMinuteHand(it)
            drawSecondsHand(it)
            drawMiddleCircle(it)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        contentPaint.color = context.getCompactColor(R.color.blue)
        val cx = getBounds().centerX()
        val cy = getBounds().centerY()
        canvas.drawCircle(cx, cy, getRadius() * 0.8f, contentPaint)
    }

    private fun drawBorder(canvas: Canvas) {
        val cx = getBounds().centerX()
        val cy = getBounds().centerY()
        canvas.drawCircle(cx, cy, getRadius() * 0.8f, borderPaint)
    }

    private fun drawMiddleCircle(canvas: Canvas) {
        val cx = getBounds().centerX()
        val cy = getBounds().centerY()
        canvas.drawCircle(cx, cy, getRadius() * 0.1f, centerPaint)
    }

    private fun getBounds(): RectF {
        return RectFFactory.fromCircle(
            PointF(contentRect.centerX(), contentRect.centerY()),
            getRadius()
        )
    }

    private fun drawHourHand(canvas: Canvas) {
        val radius = Math.toRadians(-90 + hour.toDouble() * 30 + minute * 0.5)
        val endX = getBounds().centerX() + getRadius() * 0.3f * cos(radius)
        val endY = getBounds().centerY() + getRadius() * 0.3f * sin(radius)

        handsPaint.shader = RadialGradient(
            getBounds().centerX(),
            getBounds().centerY(),
            getRadius() * 0.4f,
            context.getCompactColor(R.color.orange),
            context.getCompactColor(R.color.lightOrange),
            Shader.TileMode.CLAMP
        )

        canvas.drawLine(
            getBounds().centerX(),
            getBounds().centerY(),
            endX.toFloat(),
            endY.toFloat(),
            handsPaint
        )
    }

    private fun drawMinuteHand(canvas: Canvas) {
        val radius = Math.toRadians(-90 + minute * 6.0)
        val endX = getBounds().centerX() + getRadius() * 0.5f * cos(radius)
        val endY = getBounds().centerY() + getRadius() * 0.5f * sin(radius)

        handsPaint.shader = RadialGradient(
            getBounds().centerX(),
            getBounds().centerY(),
            getRadius() * 0.6f,
            context.getCompactColor(R.color.colorPrimary),
            context.getCompactColor(R.color.colorAccent),
            Shader.TileMode.CLAMP
        )

        canvas.drawLine(
            getBounds().centerX(),
            getBounds().centerY(),
            endX.toFloat(),
            endY.toFloat(),
            handsPaint
        )
    }

    private fun drawSecondsHand(canvas: Canvas) {
        val radius = Math.toRadians(-90 + second.toDouble() * 6)
        val endX = getBounds().centerX() + getRadius() * 0.7f * cos(radius)
        val endY = getBounds().centerY() + getRadius() * 0.7f * sin(radius)

        secondsPaint.shader = RadialGradient(
            getBounds().centerX(),
            getBounds().centerY(),
            getRadius() * 0.8f,
            context.getCompactColor(R.color.yellow),
            context.getCompactColor(R.color.veryLightGrey),
            Shader.TileMode.CLAMP
        )

        canvas.drawLine(
            getBounds().centerX(),
            getBounds().centerY(),
            endX.toFloat(),
            endY.toFloat(),
            secondsPaint
        )
    }

    private fun drawSteps(canvas: Canvas) {
        val centerX = getBounds().centerX()
        val centerY = getBounds().centerY()
        for (i in 0..360 step 12) {
            val radians = Math.toRadians(-90 + i.toDouble())
            val startX = centerX + getRadius() * cos(radians).toFloat()
            val startY = centerY + getRadius() * sin(radians).toFloat()

            val endX = centerX + getRadius() * 0.9f * cos(radians).toFloat()
            val endY = centerY + getRadius() * 0.9f * sin(radians).toFloat()

            canvas.drawLine(startX, startY, endX, endY, stepPaint)
        }
    }

    private fun getRadius() = min(contentRect.right, contentRect.bottom) / 2

    private fun startDateTime() {
        val date = Calendar.getInstance()
        second = date.get(Calendar.SECOND)
        minute = date.get(Calendar.MINUTE)
        hour = date.get(Calendar.HOUR)

        //  Log.i("TAG", "Hour: $hour - minute: $minute - second: $second")
        invalidate()
    }
}