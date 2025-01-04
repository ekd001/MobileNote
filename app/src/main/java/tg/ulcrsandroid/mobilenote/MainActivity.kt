package tg.ulcrsandroid.mobilenote

import android.app.Dialog
import android.content.Intent
import tg.ulcrsandroid.mobilenote.R
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import tg.ulcrsandroid.mobilenote.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var ui: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        setTitle("MobileNote")

        // Récupérer la Toolbar
        val toolbar = ui.toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(getColor(R.color.white))

        // Les categories
        val gridLayout = ui.gridLayout

        ui.buttonAjouter.setOnClickListener {
            showAddDialog()
        }
    }

    // Rattacher les icones au Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun showAddDialog() {
        // Créer un Dialog
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.add_categorie, null)
        dialog.setContentView(view)

        val editTextNom = view.findViewById<EditText>(R.id.editTextNom)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonValider = view.findViewById<Button>(R.id.buttonValider)

        buttonValider.setOnClickListener {
            val nom = editTextNom.text.toString()
            val description = editTextDescription.text.toString()

            if (nom.isNotEmpty()) {
                // Créer une nouvelle vue à partir de `grid_item`
                val newContainer = layoutInflater.inflate(R.layout.grid_item, ui.gridLayout, false)
                val titleView = newContainer.findViewById<TextView>(R.id.categorieTitle)
                titleView.text = nom

                val threeDots = newContainer.findViewById<ImageView>(R.id.threeDots)
                threeDots.setOnClickListener {
                    // Créer et afficher le PopupMenu
                    val popupMenu = PopupMenu(this, threeDots)
                    popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

                    // Gérer les clics sur les éléments du menu
                    popupMenu.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.modifier -> {
                                showAddDialog()
                                Toast.makeText(this, "Categorie modifiée", Toast.LENGTH_SHORT).show()
                                true
                            }
                            R.id.supprimer -> {
                                ui.gridLayout.removeView(newContainer)
                                Toast.makeText(this, "Categorie supprimée", Toast.LENGTH_SHORT).show()
                                true
                            }
                            else -> false
                        }
                    }

                    popupMenu.show()
                }

                newContainer.setOnClickListener {
                    // Démarrer une autre activité
                    val intent = Intent(this, CategorieActivity::class.java)
                    startActivity(intent)
                }

                // Ajouter la nouvelle vue au GridLayout
                ui.gridLayout.addView(newContainer)

                Toast.makeText(this, "Categorie $nom ajoutée", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Veuillez renseigner le nom.", Toast.LENGTH_SHORT).show()
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