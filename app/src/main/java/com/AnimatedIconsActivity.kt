package com

import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.canvas.R
import kotlinx.android.synthetic.main.activity_animated_icons.*

class AnimatedIconsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animated_icons)

        imageView.setOnClickListener {
            imageView.applyLoopingAnimatedVectorDrawable(R.drawable.avd_notification_bell_2)
        }
    }

    internal fun ImageView.applyLoopingAnimatedVectorDrawable(@DrawableRes avdResId: Int) {
        val animated = AnimatedVectorDrawableCompat.create(context, avdResId)
        animated?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                this@applyLoopingAnimatedVectorDrawable.post { animated.start() }
            }
        })
        this.setImageDrawable(animated)
        animated?.start()
    }
}