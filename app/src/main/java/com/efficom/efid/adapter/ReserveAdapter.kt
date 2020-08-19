package com.efficom.efid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.efficom.efid.R
import com.efficom.efid.data.model.Reservation
import com.efficom.efid.data.model.Room
import kotlinx.android.synthetic.main.item_free_room.view.*
import kotlinx.android.synthetic.main.item_reserved_room.view.*
import kotlinx.android.synthetic.main.item_textview.view.*
import kotlinx.android.synthetic.main.item_textview.view.textView

class ReserveAdapter(var data: List<Reservation>) :
    RecyclerView.Adapter<ReserveAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_reserved_room, parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
        if (position == itemCount-1){
            holder.itemView.divider.visibility = View.GONE
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv_room = view.item_tv_room
        private val tv_descr = view.item_tv_descr
        private val tv_date = view.item_tv_date

        fun bind(item: Reservation) {
            tv_room.text = item.intitule.capitalize()
            tv_descr.text = item.intitule.capitalize()
            tv_date.text = item.date.capitalize()
        }
    }
}