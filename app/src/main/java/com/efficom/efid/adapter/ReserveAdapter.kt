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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ReserveAdapter(var data: List<Reservation>) :
    RecyclerView.Adapter<ReserveAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_reserved_room, parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
        if (position == itemCount-1){
            holder.itemView.item_divider.visibility = View.GONE
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv_room = view.item_tv_room
        private val tv_descr = view.item_tv_descr
        private val tv_date = view.item_tv_date

        fun bind(item: Reservation) {
            tv_room.text = "Salle ${item.numero_salle}"
            tv_descr.text = item.intitule.capitalize()
            tv_date.text = formatDate(item.date).capitalize()
        }

        private fun formatDate(date: String): String{
            val calendar = Calendar.getInstance()

            val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val splitDate = date.split('T')
            val finalDate = LocalDate.parse(splitDate[0], DateTimeFormatter.ISO_DATE)
            calendar.set(Calendar.YEAR, finalDate.year)
            calendar.set(Calendar.MONTH, finalDate.monthValue)
            calendar.set(Calendar.DAY_OF_MONTH, finalDate.dayOfMonth)

            return formatter.format(calendar.time)
        }
    }
}