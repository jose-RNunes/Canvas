package com.extensions

import android.content.res.Resources


val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.nonZero() = if (this <= 0) 1 else this