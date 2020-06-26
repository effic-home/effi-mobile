package com.efficom.efid.data.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterRequest (
    @Expose
    @SerializedName("nom")
    var nom: String = "",

    @Expose
    @SerializedName("prenom")
    var prenom: String = "",

    @Expose
    @SerializedName("email")
    var email: String = "",

    @Expose
    @SerializedName("password")
    var password: String = "",

    var confPassword: String ="",

    @Expose
    @SerializedName("id_classe")
    var idClasse: Int = 0
)