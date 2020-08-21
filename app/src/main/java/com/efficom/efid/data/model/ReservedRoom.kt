package com.efficom.efid.data.model

data class ReservedRoom (
    var id_salle: Int = 0,
    var numero_salle: String = "",
    var etage: Int = 0,
    var type: String = "",
    var occupee: Int = 0,
    var capacite: Int = 0,
    var id_reservation: Int = 0,
    var date: String = "",
    var heure_debut: String = "",
    var heure_fin: String = "",
    var intitule: String = ""
)