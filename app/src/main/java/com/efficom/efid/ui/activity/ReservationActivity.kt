package com.efficom.efid.ui.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.efficom.efid.R
import com.efficom.efid.data.model.Room
import com.efficom.efid.data.model.User
import com.efficom.efid.data.model.request.ReservationRequest
import com.efficom.efid.data.model.sealedClass.RoomApiReturn
import com.efficom.efid.viewmodel.RoomViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_reservation.*
import kotlinx.android.synthetic.main.menu_custom_layout.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class ReservationActivity: BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var viewModel: RoomViewModel
    val reservationRequest = ReservationRequest()

    lateinit var date:Date

    val hourMap = mutableMapOf<String, Date>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RoomViewModel::class.java)

        supportActionBar?.let {
            it.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
            it.setCustomView(R.layout.menu_custom_layout)
            it.customView?.tvTitle?.text = "Réservation"

        }
        setContentView(R.layout.activity_reservation)

        intent.extras?.let {
            viewModel.actualRoom = Gson().fromJson(it["actualRoom"] as String, Room::class.java)
            areservation_tv_salle.text = "Salle ${viewModel.actualRoom.numero_salle}"

            val bundleDate = it["selectedDate"] as String
            date = Gson().fromJson(bundleDate, Date::class.java)
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val format2 = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

            reservationRequest.date = format.format(date)
            areservation_spinner_date.text = format2.format(date)
        }

        setupClick()
        setupClickOutside(areservation_layout)
        setupSpinner()
        setupObserver()
    }

    private fun setupObserver(){
        viewModel.successReserve.observe(this, androidx.lifecycle.Observer {
            displayPopUp()
        })
        viewModel.error.observe(this, androidx.lifecycle.Observer {
            displayErrorMessage(it)
            setSpinnerVisibility()
        })
        viewModel.inProcess.observe(this, androidx.lifecycle.Observer {
            setSpinnerVisibility()
        })
    }

    private fun setupClick(){
        areservation_decline_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        areservation_validate_btn.setOnClickListener {
            setSpinnerVisibility()
            verifField()
        }
    }

    private fun setupSpinner(){
        val listOfPers = mutableListOf<String>()
        for (i in 0..viewModel.actualRoom.capacite){ listOfPers.add(i.toString()) }
        areservation_spinner_nbrpers.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfPers)

        val calendar = Calendar.getInstance()
        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        calendar.set(localDate.year, localDate.monthValue, localDate.dayOfMonth)
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)

        val listOfHour = mutableListOf<String>()
        for (i in 0..30){
            calendar.add(Calendar.MINUTE, +30)
            val timetoPick = "${calendar.get(Calendar.HOUR_OF_DAY).toString()}:${calendar.get(java.util.Calendar.MINUTE).toString()}"
            hourMap[timetoPick] = calendar.time
            listOfHour.add(timetoPick)
        }

        areservation_spinner_starthour.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfHour)
        areservation_spinner_endhour.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfHour)
    }

    private fun verifField(){
        val calendar = Calendar.getInstance()
        val nbr_pers = areservation_spinner_nbrpers.selectedItem as String
        if (nbr_pers != "0"){
            reservationRequest.nb_personne = nbr_pers.toInt()
            val startHour = areservation_spinner_starthour.selectedItem as String
            val endHour = areservation_spinner_endhour.selectedItem as String
            if (hourMap[startHour]!! > calendar.time && hourMap[endHour]!! > calendar.time && hourMap[endHour]!! > hourMap[startHour]!! ){
                reservationRequest.heure_debut = startHour
                reservationRequest.heure_fin = endHour
                val intitule = areservation_edit_intitule.text
                if (intitule.isNotEmpty()){
                    reservationRequest.intitule = intitule.toString()
                    reservationRequest.id_prof = null
                    reservationRequest.id_salle = viewModel.actualRoom.id_salle
                    reservationRequest.id_user = getUserId().toString()
                    viewModel.reserveRoom(reservationRequest)
                }
                else{
                    displayToast("Veuillez indiquer le but de cette réservation", this)
                }
            }else{
                displayToast("Veuillez choisir une heure valide", this)
            }
        } else {
            displayToast("Veuillez choisir un nombre de personne", this)
        }
    }

    private fun getUserId(): Int?{
        sharedPreferences.getString("user", null).let {
            if (it != null){
                val user: User = Gson().fromJson(it, User::class.java)
                return user.id_user
            }
        }
        return null
    }

    private fun displayPopUp(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("C'EST GAGNE !")
        builder.setPositiveButton("OK"){dialog, which ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun displayErrorMessage(error: RoomApiReturn){
        val message: String = when(error){
            else -> "Une erreur est survenue"
        }
        displayToast(message, this)
    }

    private fun setSpinnerVisibility(){
        if (areservation_waiting.visibility == View.GONE){
            areservation_waiting.visibility = View.VISIBLE
        }else{
            areservation_waiting.visibility = View.GONE
        }

        areservation_spinner_nbrpers.isEnabled = !areservation_spinner_nbrpers.isEnabled
        areservation_spinner_starthour.isEnabled = !areservation_spinner_starthour.isEnabled
        areservation_spinner_endhour.isEnabled = !areservation_spinner_endhour.isEnabled
        areservation_edit_intitule.isEnabled = !areservation_edit_intitule.isEnabled
        areservation_validate_btn.isEnabled = !areservation_validate_btn.isEnabled
        areservation_decline_btn.isEnabled = !areservation_decline_btn.isEnabled
    }

    private fun displayToast(message: String, context: Context?) = Toast.makeText(context,
        message, Toast.LENGTH_LONG).show()


    private fun setupClickOutside(view: View) {
        if (view !is EditText){
            view.setOnTouchListener { v, event ->
                hideKeyboard()
                false
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until (view as ViewGroup).childCount){
                val innerView: View = (view as ViewGroup).getChildAt(i)
                setupClickOutside(innerView)
            }
        }
    }

    private fun hideKeyboard() {
        val imm = this?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }
}