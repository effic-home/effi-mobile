package com.efficom.efid.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.efficom.efid.R
import com.efficom.efid.adapter.RoomAdapter
import com.efficom.efid.data.model.Room
import com.efficom.efid.data.model.sealedClass.*
import com.efficom.efid.viewmodel.RoomViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.menu_custom_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment: BaseFragment() {

    lateinit var viewModel: RoomViewModel
    lateinit var date: Date
    private val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RoomViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, null, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.customView?.tvTitle?.text = "Accueil"

        setupClickOutside(home_layout)
        setupDate()

        viewModel.freeRoomList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            home_refresh_layout.isRefreshing = false
            setupFreeRoom(it)
            home_no_place.visibility = View.GONE
        })
        viewModel.error.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            home_refresh_layout.isRefreshing = false
            home_no_place.visibility = View.VISIBLE
            home_no_place.z = 10F
            if (isInternetAvailable()){
                displayMainErrorMessage(it)
            }
            else{
                displayMainErrorMessage(NoInternet)
            }

        })

        home_refresh_layout.setOnRefreshListener {
            viewModel.getOpenRoom()
        }
    }

    private fun setupDate(){
        date = Calendar.getInstance().time
        home_tv_date.text = format.format(date)
        home_tv_date.setTypeface(null, Typeface.BOLD)
    }

    private fun setupFreeRoom(rooms: List<Room>){

        if (rooms.isEmpty()){
            home_no_place.visibility = View.VISIBLE
            home_no_place.z = 10F
        }
        else{
            val viewManager = LinearLayoutManager(context)
            val viewAdapter = RoomAdapter(rooms.take(5), requireContext(), date)

            home_recycler_view.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }
}