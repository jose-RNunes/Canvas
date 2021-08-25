package com

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.canvas.R
import kotlinx.android.synthetic.main.activity_triangle_progress.*

class TriangleProgressActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_triangle_progress)

        triangle_progress.post {
            triangle_progress.startAnimation()
        }
    }

}