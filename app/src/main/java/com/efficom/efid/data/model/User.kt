package com.efficom.efid.data.model

import com.google.gson.annotations.SerializedName

data class User (
    var id_user: Int = 0,
    var nom: String = "",
    var prenom: String = "",
    var email: String = "",
    var id_puce: Int = 0,
    var type: String = "",
    var nom_classe: String = "",
    var effectif: Int = 0
)