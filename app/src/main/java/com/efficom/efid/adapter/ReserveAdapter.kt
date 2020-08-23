package com.efficom.efid.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.efficom.efid.R
import com.efficom.efid.data.model.Reservation
import com.efficom.efid.data.model.Room
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_free_room.view.*
import kotlinx.android.synthetic.main.item_reserved_room.view.*
import kotlinx.android.synthetic.main.item_textview.view.*
import kotlinx.android.synthetic.main.item_textview.view.textView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class ReserveAdapter(val data: List<Reservation>, val context: Context) :
    RecyclerView.Adapter<ReserveAdapter.MyViewHolder>() {

    private val calendar = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = MyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_reserved_room, parent, false)
    )

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.item_tv_room.setTypeface(null, Typeface.BOLD)
        if (position == itemCount-1){
            holder.itemView.item_divider.visibility = View.GONE
        }

        if (getDateFromString(data[position].date) > calendar.time){
            val color = ContextCompat.getColor(context, android.R.color.holo_orange_light)
            holder.itemView.item_image.setColorFilter(color)
        }else{
            val color = ContextCompat.getColor(context, android.R.color.holo_green_light)
            holder.itemView.item_image.setColorFilter(color)
        }
    }

    private fun getDateFromString(date: String): Date{
        val finalDate = Calendar.getInstance()
        val splitedDate = date.split("T")
        val localDate =  LocalDate.parse(splitedDate[0], DateTimeFormatter.ISO_DATE)
        finalDate.set(Calendar.DAY_OF_MONTH, localDate.dayOfMonth)
        finalDate.set(Calendar.MONTH, localDate.monthValue)
        finalDate.set(Calendar.YEAR, localDate.year)

        return finalDate.time
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv_room = view.item_tv_room
        private val tv_descr = view.item_tv_descr
        private val tv_date = view.item_tv_date
        private val tv_hour = view.item_tv_hour

        fun bind(item: Reservation) {
            tv_room.text = "Salle ${item.numero_salle}"
            tv_descr.text = item.intitule.capitalize()
            tv_date.text = formatDate(item.date).capitalize()
            tv_hour.text = formatHour(item.heure_debut, item.heure_fin)
        }

        private fun formatHour(start:String, end:String): String{
            val splitedStart = start.split(":")
            val splitedEnd = end.split(":")
            return "${splitedStart[0]}:${splitedStart[1]} - ${splitedEnd[0]}:${splitedEnd[1]}"
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