package com.efficom.efid.ui.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.efficom.efid.R
import com.efficom.efid.data.model.request.LoginRequest
import com.efficom.efid.data.model.sealedClass.*
import com.efficom.efid.databinding.FragmentLoginBinding
import com.efficom.efid.ui.activity.MainActivity
import com.efficom.efid.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment: BaseFragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var viewModel: LoginViewModel
    private lateinit var loginRequest: LoginRequest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(LoginViewModel::class.java)

        loginRequest = LoginRequest()
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater,
            R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.login = loginRequest
        binding.executePendingBindings()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupClickOutside(login_layout)
        setupUrl()

        login_enrolment_btn.setOnClickListener {
            navigateToRegister()
        }

        viewModel.saveServerUrl.observe(viewLifecycleOwner, Observer {
            saveServerUrl(it)
        })
        viewModel.canConnectUser.observe(viewLifecycleOwner, Observer {
            navigateToMainActivity()
        })
        viewModel.waitingVisibility.observe(viewLifecycleOwner, Observer {
            changeSpinnerVisibility()
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            changeSpinnerVisibility()
            displayErrorMessage(it)
        })
    }

    private fun setupUrl(){
        val serverUrl = sharedPreferences.getString("base_url", "")
        if (!serverUrl.isNullOrEmpty()){
            login_editTextServer.setText(serverUrl)
        }
    }

    private fun saveServerUrl(url: String) {
        if (sharedPreferences.getString("base_url", "").isNullOrEmpty()){
            sharedPreferences?.let {
                val editor = it.edit()
                editor.putString("base_url", url)
                editor.commit()
            }
        }
        viewModel.connectUser(loginRequest)
    }

    private fun navigateToMainActivity(){
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun navigateToRegister(){
        findNavController().navigate(LoginFragmentDirections.loginToRegister())
    }

    private fun changeSpinnerVisibility(){

        if (login_spinner.visibility == View.GONE){
            login_spinner.visibility = View.VISIBLE
            login_editTextTextEmailAddress.isEnabled = false
            login_editTextTextPassword.isEnabled = false
            login_editTextServer.isEnabled = false
            login_connect_btn.isEnabled = false
            login_enrolment_btn.isEnabled = false
        }
        else {
            login_spinner.visibility = View.GONE
            login_editTextTextEmailAddress.isEnabled = true
            login_editTextTextPassword.isEnabled = true
            login_editTextServer.isEnabled = true
            login_connect_btn.isEnabled = true
            login_enrolment_btn.isEnabled = true
        }
    }

    private fun displayErrorMessage(error: AuthApiReturn){
        val message: String = when(error){
            is LoginIsWrong -> resources.getString(R.string.login_wrong_login)
            is LoginEmailInvalid -> resources.getString(R.string.login_wrong_email)
            is LoginEmptyField -> resources.getString(R.string.login_empty_field)
            else -> ""
        }
        displayToast(message, context)
    }
}