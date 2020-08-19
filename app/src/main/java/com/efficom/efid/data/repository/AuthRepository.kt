package com.efficom.efid.data.repository

import com.efficom.efid.data.model.request.LoginRequest
import com.efficom.efid.data.model.sealedClass.AuthApiReturn
import com.efficom.efid.data.model.sealedClass.LoginIsValid
import com.efficom.efid.data.model.sealedClass.LoginIsWrong
import com.efficom.efid.data.network.AuthApi
import io.reactivex.internal.operators.single.SingleDoOnSuccess
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApi: AuthApi) {

    suspend fun authenticateUser(loginRequest: LoginRequest):  AuthApiReturn{

        return try {
//            val url = "${loginRequest.server}/${loginRequest.email}/${loginRequest.password}"
            val response = authApi.authenticate(loginRequest.email, loginRequest.password)
            if (response.isSuccessful){
                LoginIsValid
            } else{
                when(response.code()){
                    403 -> LoginIsWrong
                    else -> LoginIsWrong
                }
            }
        } catch (e: Exception){
            Timber.e(e)
            LoginIsWrong
        }
    }
}