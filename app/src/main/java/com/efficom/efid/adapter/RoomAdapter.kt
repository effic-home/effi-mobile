package com.efficom.efid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.efficom.efid.R
import kotlinx.android.synthetic.main.item_textview.view.*

class RoomAdapter(var data: List<String>) :
    RecyclerView.Adapter<RoomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_textview, parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.textView

        fun bind(item: String) {
            textView.text = item
        }
    }
}