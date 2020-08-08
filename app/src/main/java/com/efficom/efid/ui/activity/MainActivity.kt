package com.efficom.efid.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.efficom.efid.R
import com.efficom.efid.ui.fragment.HomeFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorAccent)

        setupNavBar()
    }

    private fun setupNavBar(){
        bottom_nav_bar.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.item_reserv -> {
                    navigateTo(R.id.roomFragment)
                    true
                }
                R.id.item_home -> {
                    navigateTo(R.id.homeFragment)
                    true
                }
                else -> { true }
            }
        }
    }

    fun navigateTo(id: Int){
        Navigation.findNavController(this, R.id.main_nav_host_fragment).navigate(id)
    }
}
