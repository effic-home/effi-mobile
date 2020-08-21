package com.efficom.efid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.efficom.efid.data.model.User
import com.efficom.efid.data.model.request.LoginRequest
import com.efficom.efid.data.model.sealedClass.*
import com.efficom.efid.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val app: Application,
    private val authRepository: AuthRepository): AndroidViewModel(app) {

    private val _waitingVisibility = MutableLiveData<Boolean>()
    val waitingVisibility = _waitingVisibility

    private val _errorMessage = MutableLiveData<AuthApiReturn>()
    val errorMessage = _errorMessage

    private val _canConnectUser = MutableLiveData<User>()
    val canConnectUser = _canConnectUser

    fun connectUser(loginRequest : LoginRequest) {

        _waitingVisibility.postValue(true)

        if (loginRequest.email.isNotEmpty() && loginRequest.password.isNotEmpty()){
            if (isEmailValid(loginRequest.email)){
                loginRequest.password = hash(loginRequest.password)
                GlobalScope.launch(Dispatchers.IO) {
                    authRepository.authenticateUser(loginRequest).let {response ->
                        when(response){
                            is LoginIsValid -> {
                                _canConnectUser.postValue(response.data)
                            }
                            else -> {
                                displayError(LoginIsWrong)
                            }
                        }
                    }

                }
            }
            else {
                displayError(LoginEmailInvalid)
            }
        }
        else {
            displayError(LoginEmptyField)
        }


    }

    private fun hash(mdp: String): String {
        val bytes = mdp.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    private fun displayError(error: AuthApiReturn){
        _errorMessage.postValue(error)
    }

    private fun isEmailValid(target: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}