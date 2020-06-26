package com.efficom.efid.data.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    var email: String = "",
    var password: String = "",
    var server: String = ""
)