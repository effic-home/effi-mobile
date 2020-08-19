package com.efficom.efid.data.model.sealedClass

sealed class AuthApiReturn

object LoginIsValid: AuthApiReturn()
object LoginIsWrong: AuthApiReturn()
object LoginEmailInvalid: AuthApiReturn()
object LoginEmptyField: AuthApiReturn()