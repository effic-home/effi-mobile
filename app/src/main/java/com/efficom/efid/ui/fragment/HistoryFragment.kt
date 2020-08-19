package com.efficom.efid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.efficom.efid.R
import com.efficom.efid.viewmodel.RoomViewModel
import kotlinx.android.synthetic.main.fragment_history.*

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
    }
}