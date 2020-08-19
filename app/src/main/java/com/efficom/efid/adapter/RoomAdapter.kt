package com.efficom.efid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.efficom.efid.R
import com.efficom.efid.data.model.Room
import kotlinx.android.synthetic.main.item_free_room.view.*
import kotlinx.android.synthetic.main.item_textview.view.*
import kotlinx.android.synthetic.main.item_textview.view.textView

class RoomAdapter(var data: List<Room>) :
    RecyclerView.Adapter<RoomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_free_room, parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
        if (position == itemCount-1){
            holder.itemView.divider.visibility = View.GONE
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.textView

        fun bind(item: Room) {
            textView.text = item.numero_salle.capitalize()
        }
    }
}