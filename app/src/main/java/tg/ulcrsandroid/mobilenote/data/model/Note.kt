package tg.ulcrsandroid.mobilenote.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Date
import java.util.UUID

open class Note (
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),  // Génération automatique de l'ID
    var titre: String = "",  // Le titre est requis
    var contenu: String = "",  // Le contenu est requis
    var dateCreated: Date = Date(),  // Initialisation de la date de création
    var dateUpdated: Date = Date(),  // Initialisation de la date de mise à jour
    var categorieId: String = ""  // Initialisation de l'ID de la catégorie
):RealmObject(){
    // Accès direct aux propriétés
    fun getId(): String = id
    fun getTitre(): String = titre
    fun getContenu(): String = contenu
    fun getDateCreated(): Date = dateCreated
    fun getDateUpdated(): Date = dateUpdated
    fun getCategorieId(): String = categorieId

    // Méthodes pour mettre à jour certaines valeurs
    fun setTitre(newTitre: String) {
        titre = newTitre
    }

    fun setContenu(newContenu: String) {
        contenu = newContenu
    }

    fun setCategorieId(newCategorieId: String) {
        categorieId = newCategorieId
    }

    fun setDateUpdated(newDateUpdated: Date) {
        dateUpdated = newDateUpdated
    }

}