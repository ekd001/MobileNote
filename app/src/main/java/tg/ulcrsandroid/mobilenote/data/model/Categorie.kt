package tg.ulcrsandroid.mobilenote.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Date
import java.util.UUID

open class Categorie(
    var id: String = UUID.randomUUID().toString(),  // Génération automatique de l'ID
    var libelle: String,  // Le libelle est requis
    var description: String,  // La description est requise
    var notes: RealmList<Note> = RealmList(),  // Initialisation de notes à une liste vide
    var dateCreated: Date = Date(),  // Initialisation de la date de création
    var dateUpdated: Date = Date()  // Initialisation de la date de mise à jour
):RealmObject(){
    // Accès direct aux propriétés
    fun getId(): String = id
    fun getLibelle(): String = libelle
    fun getDescription(): String = description
    fun getNotes(): RealmList<Note> = notes
    fun getDateCreated(): Date = dateCreated
    fun getDateUpdated(): Date = dateUpdated

    // Méthodes pour mettre à jour certaines valeurs
    fun setLibelle(newLibelle: String) {
        libelle = newLibelle
    }

    fun setDescription(newDescription: String) {
        description = newDescription
    }

    fun setNotes(newNotes: RealmList<Note>) {
        notes = newNotes
    }

    fun setDateUpdated(newDateUpdated: Date) {
        dateUpdated = newDateUpdated
    }
}
