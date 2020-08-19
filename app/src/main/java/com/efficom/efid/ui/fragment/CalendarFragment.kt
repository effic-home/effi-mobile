package com.efficom.efid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.efficom.efid.R
import com.efficom.efid.data.model.Room
import com.efficom.efid.viewmodel.RoomViewModel

class CalendarFragment: BaseFragment() {

    lateinit var viewModel: RoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RoomViewModel::class.java)
        return inflater.inflate(R.layout.fragment_calendar, null, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.freeRoomList.observe(viewLifecycleOwner, Observer {
            displayFreeRoom(it)
        })
    }

    private fun displayFreeRoom(rooms: List<Room>){

    }
}