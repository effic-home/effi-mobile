package com.efficom.efid.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.efficom.efid.data.model.request.LoginRequest
import com.efficom.efid.data.model.sealedClass.*
import com.efficom.efid.data.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val app: Application,
    private val authRepository: AuthRepository): AndroidViewModel(app) {

    private val _waitingVisibility = MutableLiveData<Boolean>()
    val waitingVisibility = _waitingVisibility

    private val _errorMessage = MutableLiveData<AuthApiReturn>()
    val errorMessage = _errorMessage

    private val _canConnectUser = MutableLiveData<String>()
    val canConnectUser = _canConnectUser

    private val _saveServerUrl = MutableLiveData<String>()
    val saveServerUrl = _saveServerUrl

    fun connectUser(loginRequest : LoginRequest) {

        GlobalScope.launch(Dispatchers.IO) {
            when(authRepository.authenticateUser(loginRequest)){
                is LoginIsValid -> _canConnectUser.postValue("")
                else -> {
                    displayError(LoginIsWrong)
                }
            }
        }
    }

    fun setUrlServer(loginRequest : LoginRequest){

        _waitingVisibility.postValue(true)

        if (loginRequest.email.isNotEmpty() && loginRequest.password.isNotEmpty() && loginRequest.server.isNotEmpty()){
            if (isEmailValid(loginRequest.email)){
                _saveServerUrl.postValue(loginRequest.server)
            }
            else {
                displayError(LoginEmailInvalid)
            }
        }
        else {
            displayError(LoginEmptyField)
        }
    }

    private fun displayError(error: AuthApiReturn){
        _waitingVisibility.postValue(true)
        _errorMessage.postValue(error)
    }

    private fun isEmailValid(target: String): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}