package com.efficom.efid.ui.fragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.efficom.efid.R
import com.efficom.efid.adapter.RoomAdapter
import com.efficom.efid.data.model.Room
import com.efficom.efid.viewmodel.RoomViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_reservation.*
import java.text.SimpleDateFormat
import java.util.*


class ReservationFragment: BaseFragment() {

    lateinit var viewModel: RoomViewModel

    private val calendar = Calendar.getInstance()
    private var date = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }
    private val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RoomViewModel::class.java)
        return inflater.inflate(R.layout.fragment_reservation, null, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateLabel()

        reserve_image_date.setOnClickListener {
            DatePickerDialog(requireContext(), date, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        reserve_prev_btn.setOnClickListener {
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            updateLabel()
        }

        reserve_next_btn.setOnClickListener {
            calendar.add(Calendar.DAY_OF_MONTH, +1)
            updateLabel()
        }

        viewModel.freeRoomByDate.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            setupFreeRoomList(it)
        })
        viewModel.error.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            displayMainErrorMessage(it)
        })
    }

    private fun updateLabel(){
        val datePick = formatter.format(calendar.time)
        reserve_tv_date.setText(datePick)
        viewModel.getFreeRoomByDate(datePick)
    }

    private fun setupFreeRoomList(rooms: List<Room>){
        val viewManager = LinearLayoutManager(context)
        val viewAdapter = RoomAdapter(rooms, requireContext(), calendar.time)


        reserve_recycler.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}