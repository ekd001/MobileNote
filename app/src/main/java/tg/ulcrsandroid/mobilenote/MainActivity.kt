package tg.ulcrsandroid.mobilenote

import android.content.Intent
import tg.ulcrsandroid.mobilenote.R
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.PopupMenu
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
        for (i in 1..6) {  // Supposons que tu veux 6 conteneurs
            val container = layoutInflater.inflate(R.layout.grid_item, gridLayout, false)
            gridLayout.addView(container)

            // Récupérer l'ImageView des trois points dans chaque conteneur
            val threeDots = container.findViewById<ImageView>(R.id.threeDots)
            threeDots.setOnClickListener {
                // Créer et afficher le PopupMenu
                val popupMenu = PopupMenu(this, threeDots)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

                // Gérer les clics sur les éléments du menu
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.modifier -> {
                            Toast.makeText(this, "Modifier sélectionné", Toast.LENGTH_SHORT).show()
                            true
                        }
                        R.id.supprimer -> {
                            Toast.makeText(this, "Supprimer sélectionné", Toast.LENGTH_SHORT).show()
                            true
                        }
                        else -> false
                    }
                }

                popupMenu.show()
            }

            container.setOnClickListener {
                // Démarrer une autre activité
                val intent = Intent(this, CategorieActivity::class.java)
                // Passer des données supplémentaires si nécessaire
                startActivity(intent)
            }
        }
    }

    // Rattacher les icones au Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}