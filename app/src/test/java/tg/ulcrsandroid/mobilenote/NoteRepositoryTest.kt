package tg.ulcrsandroid.mobilenote

import android.content.Context
import android.content.ContextWrapper
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.realm.Realm
import io.realm.RealmConfiguration
import tg.ulcrsandroid.mobilenote.data.repository.NoteRepository
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import tg.ulcrsandroid.mobilenote.data.model.Categorie
import tg.ulcrsandroid.mobilenote.data.model.Note
import tg.ulcrsandroid.mobilenote.data.repository.CategorieRepository

class FakeContext(base: Context) : ContextWrapper(base)
@RunWith(AndroidJUnit4::class)
class NoteRepositoryTest {
    private lateinit var noteRepository: NoteRepository
    private lateinit var realm: Realm

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        Realm.init(context)
        // Configuration de Realm pour un test en mémoire
        val config = RealmConfiguration.Builder()
            .inMemory() // Utilise une base de données en mémoire
            .name("test-realm")
            .build()
        Realm.setDefaultConfiguration(config)

        realm = Realm.getDefaultInstance()
        noteRepository = NoteRepository()
    }

    @After
    fun tearDown() {
        noteRepository.close()
        realm.close()
    }

    @Test
    fun testAddNote() {
        val note = Note().apply {
            titre = "Test Note"
            contenu = "Ceci est une note de test."
        }

        noteRepository.addNote(note)

        val savedNote = realm.where(Note::class.java).equalTo("id", note.id).findFirst()
        assertNotNull("La note devrait être enregistrée", savedNote)
        assertEquals("Test Note", savedNote?.titre)
        assertEquals("Ceci est une note de test.", savedNote?.contenu)
    }

    @Test
    fun testGetAllNotes() {
        val note1 = Note().apply {
            titre = "Note 1"
            contenu = "Contenu de la note 1"
        }
        val note2 = Note().apply {
            titre = "Note 2"
            contenu = "Contenu de la note 2"
        }

        noteRepository.addNote(note1)
        noteRepository.addNote(note2)

        val notes = noteRepository.getAllNotes()
        assertEquals(2, notes.size)
    }

    @Test
    fun testUpdateNote() {
        val note = Note().apply {
            titre = "Ancien Titre"
            contenu = "Ancien Contenu"
        }

        noteRepository.addNote(note)

        noteRepository.updateNote(note.id, "Nouveau Titre", "Nouveau Contenu")

        val updatedNote = realm.where(Note::class.java).equalTo("id", note.id).findFirst()
        assertNotNull(updatedNote)
        assertEquals("Nouveau Titre", updatedNote?.titre)
        assertEquals("Nouveau Contenu", updatedNote?.contenu)
    }

    @Test
    fun testDeleteNote() {
        val note = Note().apply {
            titre = "Note à supprimer"
            contenu = "Cette note sera supprimée."
        }

        noteRepository.addNote(note)
        noteRepository.deleteNote(note.id)

        val deletedNote = realm.where(Note::class.java).equalTo("id", note.id).findFirst()
        assertNull("La note devrait être supprimée", deletedNote)
    }

    @Test
    fun testDeleteNotesByCategorie() {
        val categorieId = "cat1"
        val note1 = Note().apply {
            titre = "Note 1"
            contenu = "Contenu de la note 1"
        }
        val note2 = Note().apply {
            titre = "Note 2"
            contenu = "Contenu de la note 2"
        }

        realm.executeTransaction {
            val categorie = Categorie().apply {
                id = categorieId
                libelle = "Catégorie Test"
                notes.addAll(listOf(note1, note2))
            }
            it.insert(categorie)
        }

        noteRepository.deleteNotesByCategorie(categorieId)

        val notes = noteRepository.getNotesByCategorie(categorieId)
        assertTrue("Toutes les notes devraient être supprimées", notes.isEmpty())
    }
}