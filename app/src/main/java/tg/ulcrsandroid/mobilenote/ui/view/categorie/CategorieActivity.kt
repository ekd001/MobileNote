package tg.ulcrsandroid.mobilenote.ui.view.categorie

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.mobilenote.R
import tg.ulcrsandroid.mobilenote.data.model.Note
import tg.ulcrsandroid.mobilenote.databinding.ActivityCategorieBinding
import tg.ulcrsandroid.mobilenote.ui.view.note.NoteAdapter
import tg.ulcrsandroid.mobilenote.viewmodel.CategorieViewModel
import tg.ulcrsandroid.mobilenote.viewmodel.NoteViewModel

class CategorieActivity:AppCompatActivity() {
    lateinit var ui: ActivityCategorieBinding
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityCategorieBinding.inflate(layoutInflater)
        setContentView(ui.root)
        setTitle("Categorie")

        // Récupérer la Toolbar
        val toolbar = ui.toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(getColor(R.color.white))

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewNote)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NoteAdapter(emptyList(), noteViewModel, this)
        recyclerView.adapter = adapter

        ui.buttonAjouter.setOnClickListener{
            showAddDialog()
        }

        // Observer les données des catégories et mettre à jour l'UI
        noteViewModel.notes.observe(this, {
                categories -> categories?.let{
            adapter.updateNotes(it)
        }
        })
    }

    // Rattacher les icones au Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun showAddDialog() {
        // Créer un Dialog
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.add_note, null)
        dialog.setContentView(view)

        val editTextTitre = view.findViewById<EditText>(R.id.editTextNom)
        val buttonValider = view.findViewById<Button>(R.id.buttonValider)

        buttonValider.setOnClickListener {
            val titre = editTextTitre.text.toString()

            if (titre.isNotEmpty()) {
                val categorieId = intent.getStringExtra("categorieId")
                val note = Note(titre = titre, categorieId = categorieId.toString())
                noteViewModel.addNote(note)

                Toast.makeText(this, "Categorie $titre ajoutée", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Veuillez renseigner le nom de la note.", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_frame)

        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.7).toInt(), // 90% de la largeur de l'écran
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.show()
    }
}