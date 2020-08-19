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
import com.efficom.efid.viewmodel.RoomViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.menu_custom_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment: BaseFragment() {

    lateinit var viewModel: RoomViewModel

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
        setupActionBar()
        setupClickOutside(home_layout)
        setupDate()

        viewModel.freeRoomList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            setupFreeRoom(it)
        })
    }

    private fun setupActionBar(){
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
            it.setCustomView(R.layout.menu_custom_layout)
            it.customView.tvTitle.text = "Accueil"
        }
    }

    private fun setupDate(){
        val date = Calendar.getInstance().time
        val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        home_tv_date.text = format.format(date)
        home_tv_date.setTypeface(null, Typeface.BOLD)
    }

    private fun setupFreeRoom(rooms: List<Room>){
        val viewManager = LinearLayoutManager(context)
        val viewAdapter = RoomAdapter(rooms.take(5))


        home_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}