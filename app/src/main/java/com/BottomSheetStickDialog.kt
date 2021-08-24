package com

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canvas.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.stick_bottomseet_dialog.*


class BottomSheetStickDialog: BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.stick_bottomseet_dialog, container,false)

        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = (1..20).map {
            "Item number: $it"
        }

        val adapter = Adapter(items)
        rv_items.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv_items.setHasFixedSize(true)
        rv_items.adapter = adapter
    }
}