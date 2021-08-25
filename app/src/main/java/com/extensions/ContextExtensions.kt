package com.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat

fun Context.getDimen(dimen: Int) = resources.getDimension(dimen)

fun Context.getCompactColor(color: Int) = ContextCompat.getColor(this, color)

fun Context.getCompactDrawable(drawable: Int) = ContextCompat.getDrawable(this, drawable)

fun Context.getColorState(color: Int) = ContextCompat.getColorStateList(this, color)


fun Context.dpToPx(dp: Float): Float {
    val displayMetrics = this.resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).toFloat()
}

/**
 * the normal [Drawable.toBitmap] method crashes when using color drawables, this method works
 */
fun Drawable.toBitmap(): Bitmap {
    if (this is BitmapDrawable) {
        return bitmap
    }

    val width = if (bounds.isEmpty) intrinsicWidth else bounds.width()
    val height = if (bounds.isEmpty) intrinsicHeight else bounds.height()

    return Bitmap.createBitmap(width.nonZero(), height.nonZero(), Bitmap.Config.ARGB_8888).also {
        val canvas = Canvas(it)
        setBounds(0, 0, canvas.width, canvas.height)
        draw(canvas)
    }
}