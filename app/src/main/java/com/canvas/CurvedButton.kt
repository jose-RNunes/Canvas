package com.canvas

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Region
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import com.extensions.getColorState
import com.extensions.getCompactColor


class CurvedButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

   // private lateinit var regionToCheck: Region

    private val path = Path()

    init {
        val drawable = CurvedDrawable(context.getCompactColor(R.color.veryLightOrange))
        val disableDrawable = CurvedDrawable(context.getCompactColor(R.color.orange))
        this.background = rippleBackground(drawable)
            /*
            getPressedState(
            rippleBackground(drawable),
            disableDrawable
        )*/
    }

    private fun getPressedState(
        enableDrawable: Drawable,
        disableDrawable: Drawable
    ): StateListDrawable {
        return StateListDrawable().apply {
            addState(intArrayOf(android.R.attr.state_pressed), enableDrawable)
            addState(intArrayOf(), disableDrawable)
        }
    }

    private fun rippleBackground(background: Drawable): RippleDrawable {
        val colorState = context.getColorState(R.color.bg_ripple)
        return RippleDrawable(colorState!!, background, null)
    }

   /* private fun createTouchRegion(){
        val pathBounds = RectF()
        path.computeBounds(pathBounds, true)
        regionToCheck = Region(
            pathBounds.left.toInt(),
            pathBounds.top.toInt(),
            pathBounds.right.toInt(),
            pathBounds.bottom.toInt()
        )
        regionToCheck.setPath(path, regionToCheck)
    }

    private fun isTouchRegion(x: Int?, y: Int?): Boolean{
        return x?.let { regionToCheck.contains(it, y!!)} ?: false
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

    */
}