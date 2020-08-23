package com.efficom.efid.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.Window
import com.efficom.efid.R
import javax.inject.Inject

class SplashScreenActivity: BaseActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_splashscreen)
        supportActionBar?.hide()

        Handler().postDelayed(Runnable {
            isAlreadyConnected()
        }, 3000)
    }


    private fun isAlreadyConnected() {
        if (sharedPreferences.getBoolean("isConnected", false)){
            redirectTo(MainActivity::class.java)
        }
        else {
            redirectTo(LoginActivity::class.java)
        }
    }

    private fun <T> redirectTo(redirectClass: Class<T>) {
        val intent = Intent(applicationContext, redirectClass)
        startActivity(intent)
        this.finish()
    }
}