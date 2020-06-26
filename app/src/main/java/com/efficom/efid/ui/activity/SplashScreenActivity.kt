package com.efficom.efid.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.efficom.efid.R

class SplashScreenActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed(Runnable {
            isJwtAvailable()
        }, 3000)
    }


    private fun isJwtAvailable() {
        if (true){
            redirectTo(LoginActivity::class.java)
        }
        else {
            redirectTo(MainActivity::class.java)
        }
    }

    private fun <T> redirectTo(redirectClass: Class<T>) {
        val intent = Intent(applicationContext, redirectClass)
        startActivity(intent)
        this.finish()
    }
}