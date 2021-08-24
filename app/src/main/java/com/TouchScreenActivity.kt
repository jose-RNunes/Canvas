package com

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.canvas.ImageViewWithZoom
import com.canvas.R

class TouchScreenActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ImageViewWithZoom(this))
    }

}