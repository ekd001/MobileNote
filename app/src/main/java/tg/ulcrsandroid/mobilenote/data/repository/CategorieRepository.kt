package tg.ulcrsandroid.mobilenote.data.repository

import io.realm.Realm
import io.realm.kotlin.where
import tg.ulcrsandroid.mobilenote.data.model.Categorie
import java.util.Date

class CategorieRepository {
    private val realm: Realm = Realm.getDefaultInstance()
    private val noteRepository = NoteRepository()

    fun addCategorie(categorie: Categorie){
        realm.executeTransaction {
            it.insert(categorie)
        }
    }

    fun updateCategorie(id:String, libelle:String, description: String){
        realm.executeTransaction {
            val categorie = it.where<Categorie>().equalTo("id", id).findFirst()
            categorie?.apply {
                this.setLibelle(libelle)
                this.setDescription(description)
                this.setDateUpdated(Date())
            }
        }
    }

    fun deleteCategorie(id: String){
        realm.executeTransaction {
            noteRepository.deleteNotesByCategorie(id)
            val categorie = it.where<Categorie>().equalTo("id", id).findFirst()
            categorie?.deleteFromRealm()
        }
    }

    fun getAllCategorie():List<Categorie>{
        return realm.where<Categorie>().findAll()
    }

    fun close(){
        realm.close()
    }
}