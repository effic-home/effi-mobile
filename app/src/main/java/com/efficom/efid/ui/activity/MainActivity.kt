package com.efficom.efid.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.efficom.efid.R
import com.efficom.efid.ui.fragment.HomeFragmentDirections
import com.google.android.material.internal.NavigationMenuItemView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_custom_layout.view.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorAccent)

        setupNavBar()
    }

    private fun setupNavBar(){

        supportActionBar?.let {
            it.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
            it.setCustomView(R.layout.menu_custom_layout)
            it.customView.menu_settings.setOnClickListener {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                finish()
            }
            it.customView.menu_logo.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://effid.apollonian.fr/login.php"))
                startActivity(intent)
            }
        }

        bottom_nav_bar.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.item_reserv -> {
                    navigateTo(R.id.historyFragment)
                    supportActionBar?.customView?.tvTitle?.text = "Historique"
                    true
                }
                R.id.item_home -> {
                    navigateTo(R.id.homeFragment)
                    supportActionBar?.customView?.tvTitle?.text = "Accueil"
                    true
                }
                R.id.item_calendar -> {
                    navigateTo(R.id.reservationFragment)
                    supportActionBar?.customView?.tvTitle?.text = "Réservation"
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
