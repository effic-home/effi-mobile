package com.efficom.efid.data.model

data class Room (
    var id_salle: Int? = null,
    var numero_salle: String = "",
    var etage: Int = 0,
    var type: String ="",
    var occupee: Int = 0,
    var capacite: Int = 0
)