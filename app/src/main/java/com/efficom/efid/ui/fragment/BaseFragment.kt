package com.efficom.efid.ui.fragment

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.efficom.efid.R
import com.efficom.efid.data.model.sealedClass.*
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment: DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    fun setupClickOutside(view:View) {
        if (view !is EditText){
            view.setOnTouchListener { v, event ->
                hideKeyboard()
                false
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until (view as ViewGroup).childCount){
                val innerView: View = (view as ViewGroup).getChildAt(i)
                setupClickOutside(innerView)
            }
        }
    }

    fun displayToast(message: String, context: Context?) = Toast.makeText(context,
        message, Toast.LENGTH_LONG).show()

    fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    fun displayMainErrorMessage(error: RoomApiReturn){
        val message: String = when(error){
            else -> ""
        }
        displayToast(message, context)
    }
}