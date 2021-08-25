package com

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.canvas.R
import kotlinx.android.synthetic.main.item_layout.view.*


class Adapter(private val strings: List<String>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_item.text = strings[position]
    }

    override fun getItemCount(): Int {
        return strings.size
    }

    class MyViewHolder(itemView: View) : ViewHolder(itemView)
}