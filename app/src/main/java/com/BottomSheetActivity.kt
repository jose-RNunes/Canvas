package com

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.canvas.R
import kotlinx.android.synthetic.main.activity_bottom_sheet.*

class BottomSheetActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)

        button.setOnClickListener {
            BottomSheetStickDialog().show(supportFragmentManager, "TAG")
        }
    }
}