package tg.ulcrsandroid.mobilenote.data.repository

import io.realm.Realm
import io.realm.kotlin.where
import tg.ulcrsandroid.mobilenote.data.model.Note
import java.util.Date

class NoteRepository {
    private val realm: Realm = Realm.getDefaultInstance()

    fun addNote(note: Note) {
        realm.executeTransaction {
            it.insert(note)
        }
    }


    fun getNotesByCategorie(categorieId: String): List<Note> {
        return realm.where<Note>().equalTo("categorieId", categorieId).findAll()
    }


    fun getAllNotes(): List<Note> {
        return realm.where<Note>().findAll()
    }


    fun updateNote(id: String, titre: String, contenu: String) {
        realm.executeTransaction {
            val note = it.where<Note>().equalTo("id", id).findFirst()
            note?.apply {
                this.titre = titre
                this.contenu = contenu
                this.dateUpdated = Date()
            }
        }
    }


    fun deleteNote(id: String) {
        realm.executeTransaction {
            val note = it.where<Note>().equalTo("id", id).findFirst()
            note?.deleteFromRealm()
        }
    }


    fun deleteNotesByCategorie(categorieId: String) {
        realm.executeTransaction {
            val notes = it.where<Note>().equalTo("categorieId", categorieId).findAll()
            notes.deleteAllFromRealm()
        }
    }


    fun close() {
        realm.close()
    }
}