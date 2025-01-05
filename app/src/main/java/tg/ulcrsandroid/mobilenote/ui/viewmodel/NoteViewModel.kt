package tg.ulcrsandroid.mobilenote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tg.ulcrsandroid.mobilenote.data.model.Note
import tg.ulcrsandroid.mobilenote.data.repository.NoteRepository

class NoteViewModel(private val noteRepository: NoteRepository):ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> get() = _notes


    fun loadNotes(categorieId: String? = null) {
        val allNotes = if (categorieId != null) {
            noteRepository.getNotesByCategorie(categorieId)
        } else {
            noteRepository.getAllNotes()
        }
        _notes.value = allNotes
    }


    fun addNote(note: Note) {
        noteRepository.addNote(note)
        loadNotes(note.categorieId)
    }


    fun updateNote(id: String, titre: String, contenu: String) {
        noteRepository.updateNote(id, titre, contenu)
        loadNotes()
    }


    fun deleteNote(id: String, categorieId: String? = null) {
        noteRepository.deleteNote(id)
        loadNotes(categorieId)
    }

    override fun onCleared() {
        super.onCleared()
        noteRepository.close()
    }
}