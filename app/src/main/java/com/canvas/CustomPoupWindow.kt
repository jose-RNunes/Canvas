package com.canvas

import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.extensions.px

class CustomAlertPopupWindow(val view: View) {

    private companion object {
        const val TIMER_DISMISS = 2000L
        const val RADIUS = 8
    }

    private val context = view.context

    private var backgroundColor: Int? = null

    private var icon: Drawable? = null

    private var message: String? = null

    private var margin: Float = 0f

    fun show() {
        val customAlertView = CustomAlertWindowView(context)
            .apply {
                this.setAlertBackgroundColor(backgroundColor)
                this.setIcon(icon)
                this.setMessage(message)
            }

        val popupWindow = PopupWindow(
            customAlertView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            this.elevation = 4.px.toFloat()
        }

        popupWindow.showAsDropDown(
            view,
            view.width / RADIUS,
            -(view.height + margin).toInt(),
            Gravity.CENTER
        )

        dismissTimer { popupWindow.dismiss() }
    }

    fun setBackgroundColor(backgroundColor: Int): CustomAlertPopupWindow {
        this.backgroundColor = backgroundColor
        return this
    }

    fun setIcon(icon: Drawable?): CustomAlertPopupWindow {
        this.icon = icon
        return this
    }

    fun setMessage(message: String): CustomAlertPopupWindow {
        this.message = message
        return this
    }

    fun setMargin(margin: Float): CustomAlertPopupWindow {
        this.margin = margin
        return this
    }

    private fun dismissTimer(endTimer: () -> Unit) {
        Handler().postDelayed({
            endTimer.invoke()
        }, TIMER_DISMISS)
    }
}