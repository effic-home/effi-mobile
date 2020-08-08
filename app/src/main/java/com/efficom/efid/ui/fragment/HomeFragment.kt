package com.efficom.efid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.efficom.efid.R
import com.efficom.efid.viewmodel.RoomViewModel
import kotlinx.android.synthetic.main.fragment_home.*

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
        setupClickOutside(home_layout)
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.roomList.observe(viewLifecycleOwner, Observer {
           setFreeRoom(it)
        })
    }

    private fun setFreeRoom(rooms: List<String>){
        val divider = View(context)
        divider.layoutParams = ViewGroup.LayoutParams(50, 1)

        for (i in 0..5){
            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.HORIZONTAL

            val textView = TextView(context)
            textView.text = rooms[i]
            linearLayout.addView(textView)

            val button = Button(context)
            button.text = "Réserver"
            linearLayout.addView(button)

            home_room_linear.addView(linearLayout)
            home_room_linear.addView(divider)
        }
    }
}