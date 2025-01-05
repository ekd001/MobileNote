package tg.ulcrsandroid.mobilenote.ui.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.mobilenote.R
import tg.ulcrsandroid.mobilenote.data.model.Categorie
import tg.ulcrsandroid.mobilenote.databinding.ActivityMainBinding
import tg.ulcrsandroid.mobilenote.ui.view.categorie.CategorieAdapter
import tg.ulcrsandroid.mobilenote.viewmodel.CategorieViewModel

class MainActivity : AppCompatActivity() {
    lateinit var ui: ActivityMainBinding
    private val categorieViewModel: CategorieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        setTitle("MobileNote")

        // Récupérer la Toolbar
        val toolbar = ui.toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(getColor(R.color.white))

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewCategories)
        val adapter = CategorieAdapter(emptyList(), categorieViewModel, this)
        recyclerView.adapter = adapter
        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        // Observer les données des catégories et mettre à jour l'UI
        categorieViewModel.categories.observe(this, {
            categories -> categories?.let{
                adapter.updateCategories(it) }
        })

        ui.buttonAjouter.setOnClickListener{
            showAddDialog()
        }


    }

    // Rattacher les icones au Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun showAddDialog(){
        // Créer un Dialog
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.add_categorie, null)
        dialog.setContentView(view)

        val editTextLibelle = view.findViewById<EditText>(R.id.editTextNom)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonValider = view.findViewById<Button>(R.id.buttonValider)

        buttonValider.setOnClickListener{
            val new_libelle = editTextLibelle.text.toString()
            val new_description = editTextDescription.text.toString()

            if (new_libelle.isNotEmpty() && new_description.isNotEmpty()){
                val categorie = Categorie(libelle = new_libelle, description = new_description)
                categorieViewModel.addCategorie(categorie)
                Toast.makeText(this, "Categorie $new_libelle ajoutée", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }else{
                Toast.makeText(this, "Veuillez saisir un libellé et une description.", Toast.LENGTH_SHORT).show()
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