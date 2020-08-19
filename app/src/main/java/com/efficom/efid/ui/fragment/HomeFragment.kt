package com.efficom.efid.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.efficom.efid.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.menu_custom_layout.*
import kotlinx.android.synthetic.main.menu_custom_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, null, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupActionBar()
        setupClickOutside(home_layout)
        setupDate()
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
}