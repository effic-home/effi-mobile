package com.efficom.efid.data.model.sealedClass

import com.efficom.efid.data.model.User

sealed class AuthApiReturn

data class LoginIsValid(var data: User): AuthApiReturn()
object LoginIsWrong: AuthApiReturn()
object LoginEmailInvalid: AuthApiReturn()
object LoginEmptyField: AuthApiReturn()
object NoInternetConnection: AuthApiReturn()