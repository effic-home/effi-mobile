package com.efficom.efid.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.efficom.efid.R
import com.efficom.efid.data.model.sealedClass.AuthApiReturn
import com.efficom.efid.data.model.sealedClass.LoginIsWrong
import com.efficom.efid.databinding.FragmentLoginBinding
import com.efficom.efid.databinding.FragmentRegisterBinding
import com.efficom.efid.ui.activity.MainActivity
import com.efficom.efid.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

class RegisterFragment: BaseFragment() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(LoginViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater,
            R.layout.fragment_register, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupClickOutside(register_layout)

        register_return_btn.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigateUp()
    }


    private fun changeSpinnerVisibility(){

        if (register_spinner.visibility == View.GONE){
            register_spinner.visibility = View.VISIBLE
            register_editTextTextEmailAddress.isEnabled = false
            register_editTextTextPassword.isEnabled = false
            register_connect_btn.isEnabled = false
            register_return_btn.isEnabled = false
        }
        else {
            register_spinner.visibility = View.GONE
            register_editTextTextEmailAddress.isEnabled = true
            register_editTextTextPassword.isEnabled = true
            register_connect_btn.isEnabled = true
            register_return_btn.isEnabled = true
        }
    }

    private fun displayErrorMessage(error: AuthApiReturn){
        val message: String = when(error){
            is LoginIsWrong -> "oui"
            else -> ""
        }
        displayToast(message, context)
    }
}