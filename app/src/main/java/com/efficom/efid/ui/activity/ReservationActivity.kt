package com.efficom.efid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.efficom.efid.R
import com.efficom.efid.data.model.Room
import com.efficom.efid.viewmodel.RoomViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_reservation.*
import kotlinx.android.synthetic.main.menu_custom_layout.view.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ReservationActivity: BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RoomViewModel::class.java)

        supportActionBar?.let {
            it.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
            it.setCustomView(R.layout.menu_custom_layout)
            it.customView?.tvTitle?.text = "Réservation"

        }

        intent.extras?.let {
            viewModel.actualRoom = Gson().fromJson(it["actualRoom"] as String, Room::class.java)
            areservation_tv_salle.text = viewModel.actualRoom.numero_salle.capitalize()
        }

        setupClick()
        setupSpinner()
    }

    private fun setupClick(){
        areservation_decline_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupSpinner(){
        val listOfPers = mutableListOf<String>()
        for (i in 0..10){ listOfPers.add(i.toString()) }
        areservation_spinner_nbrpers.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfPers)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, 8)
        calendar.set(Calendar.MINUTE, 0)

        val listOfHour = mutableListOf<String>()
        for (i in 0..20){
            calendar.add(Calendar.MINUTE, +30)
            val timetoPick = "${calendar.get(Calendar.HOUR).toString()}:${calendar.get(java.util.Calendar.MINUTE).toString()}"
            listOfHour.add(timetoPick)
        }

        areservation_spinner_starthour.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfHour)
        areservation_spinner_endhour.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfHour)
    }
}