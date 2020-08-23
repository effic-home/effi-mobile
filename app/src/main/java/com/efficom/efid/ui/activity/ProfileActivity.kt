package com.efficom.efid.ui.activity

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.efficom.efid.R
import com.efficom.efid.data.model.User
import com.efficom.efid.databinding.ActivityProfileBinding
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class ProfileActivity: BaseActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityProfileBinding>(this, R.layout.activity_profile)
        binding.user = getUser()

        supportActionBar?.hide()
        profile_title.setTypeface(null, Typeface.BOLD)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        profile_logout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getUser(): User{
        sharedPreferences.getString("user", null).let {
            if (it != null){
                val user: User = Gson().fromJson(it, User::class.java)
                return user
            }
        }
        return User()
    }
}