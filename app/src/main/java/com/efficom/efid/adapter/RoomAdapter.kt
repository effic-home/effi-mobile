package com.efficom.efid.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.efficom.efid.R
import com.efficom.efid.data.model.Room
import com.efficom.efid.ui.activity.LoginActivity
import com.efficom.efid.ui.activity.ReservationActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_free_room.view.*
import kotlinx.android.synthetic.main.item_textview.view.*
import kotlinx.android.synthetic.main.item_textview.view.textView
import javax.inject.Inject

class RoomAdapter(val data: List<Room>, val context: Context) :
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

        holder.itemView.item_button_reserve.setOnClickListener {
            val intent = Intent(context, ReservationActivity::class.java)
            intent.putExtra("actualRoom",Gson().toJson(data[position]))
            context.startActivity(intent)
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.item_textView

        fun bind(item: Room) {
            textView.text = item.numero_salle.capitalize()
        }
    }
}