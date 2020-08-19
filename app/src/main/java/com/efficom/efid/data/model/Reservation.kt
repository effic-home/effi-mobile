package com.efficom.efid.data.model

data class Reservation (
    var id_reservation: Int = 0,
    var nom: String = "",
    var prenom: String = "",
    var date: String = "",
    var heure_debut: String = "",
    var heure_fin: String = "",
    var intitule: String = "",
    var nb_personne: Int = 0,
    var numero_salle: Int = 0,
    var nom_prof: String = "",
    var etat_validation: Int = 0
)