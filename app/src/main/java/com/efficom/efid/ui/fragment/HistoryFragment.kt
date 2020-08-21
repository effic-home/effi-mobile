package com.efficom.efid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.efficom.efid.R
import com.efficom.efid.adapter.ReserveAdapter
import com.efficom.efid.adapter.RoomAdapter
import com.efficom.efid.data.model.Reservation
import com.efficom.efid.data.model.sealedClass.NoInternet
import com.efficom.efid.viewmodel.RoomViewModel
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_home.*

class HistoryFragment: BaseFragment() {

    lateinit var viewModel: RoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RoomViewModel::class.java)
        return inflater.inflate(R.layout.fragment_history, null, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        history_refreshlayout.setOnRefreshListener{
            viewModel.getOldReserv()
        }

        viewModel.oldReserve.observe(viewLifecycleOwner, Observer {
            setupOldReserve(it)
            history_no_place.visibility = View.GONE
            history_refreshlayout.isRefreshing = false
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            history_no_place.visibility = View.VISIBLE
            history_no_place.z = 10F
            if (isInternetAvailable()){
                displayMainErrorMessage(it)
            }
            else{
                displayMainErrorMessage(NoInternet)
            }
        })
    }

    private fun setupOldReserve(data: List<Reservation>) {

        if (data.isEmpty()){
            history_no_place.visibility = View.VISIBLE
            history_no_place.z = 10F
        } else{
            val viewManager = LinearLayoutManager(context)
            val viewAdapter = ReserveAdapter(data)

            history_recycler_view.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }

    }
}