package com

import android.graphics.Outline
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.canvas.CustomAlertPopupWindow
import com.canvas.R
import com.extensions.getCompactColor
import com.extensions.getCompactDrawable
import com.extensions.getDimen
import com.extensions.px
import kotlinx.android.synthetic.main.activity_draw_shadow.*

class DrawShadowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_shadow)

       // shadow.updateOutline()
      //  shadow.clipToOutline = true

        alert_window.setIcon(this.getCompactDrawable(R.drawable.ic_small_check))
        alert_window.setMessage("Telefone")


        tv_phone_number.setOnClickListener {
            CustomAlertPopupWindow(tv_phone_number)
                .setBackgroundColor(this.getCompactColor(R.color.lightGrey))
                .setIcon(this.getCompactDrawable(R.drawable.ic_small_check))
                .setMessage("Telefone Copiado")
                .setMargin(this.getDimen(R.dimen.margin_32))
                .show()
        }


        tv_shadow.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                val rectF = Rect(0, 0, view!!.width, view.height)
                outline?.setRoundRect(rectF, 8.px.toFloat())
                // outline?.setOval(rectF)
            }
        }
        tv_shadow.clipToOutline = true

        tv_shadow1.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
               // val rectF = Rect(0, 0, view!!.width, view.height)
                //outline?.setRoundRect(rectF, 8.px.toFloat())

                val width = view!!.width
                val height = view.height

                val path = Path()
                path.moveTo(10f, 10f)
                path.lineTo(10f, height - 30.px.toFloat())
              //  path.quadTo(width.toFloat() / 2, height.toFloat(), width.toFloat() - 10f, height - 30.px.toFloat())
                path.lineTo(width.toFloat() - 10f, 0f)
                path.close()

                outline?.setConvexPath(path)
            }
        }
        tv_shadow1.clipToOutline = true

        bt_curvedButton.setOnClickListener {
            Toast.makeText(this, "Cliced in Button", Toast.LENGTH_SHORT).show()
        }
    }
}