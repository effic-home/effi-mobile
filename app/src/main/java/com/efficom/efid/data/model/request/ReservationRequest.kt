package com.efficom.efid.data.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReservationRequest (
    @Expose
    @SerializedName("id_user")
    var id_user:String = "",

    @Expose
    @SerializedName("date")
    var date:String = "",

    @Expose
    @SerializedName("heure_debut")
    var heure_debut:String = "",

    @Expose
    @SerializedName("heure_fin")
    var heure_fin:String = "",

    @Expose
    @SerializedName("intitule")
    var intitule:String = "",

    @Expose
    @SerializedName("nb_personnes")
    var nb_personne:Int = 0,

    @Expose
    @SerializedName("id_salle")
    var id_salle:Int? = 0,

    @Expose
    @SerializedName("id_prof")
    var id_prof:Int? = 0
)