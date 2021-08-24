package com

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.canvas.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressLine.setOnClickListener {
            startActivity(Intent(this, ProgressLineActivity::class.java))
        }

        graphView.setOnClickListener {
            startActivity(Intent(this, GraphViewActivity::class.java))
        }

        icons_avd.setOnClickListener {
            startActivity(Intent(this, LineUpActivity::class.java))
        }

    }
}
