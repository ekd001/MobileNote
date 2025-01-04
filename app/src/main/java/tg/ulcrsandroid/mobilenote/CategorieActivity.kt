package tg.ulcrsandroid.mobilenote

import android.media.RouteListingPreference
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.mobilenote.databinding.ActivityCategorieBinding

// Adaptateu
class MyAdapter(private val itemCount: Int) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // ViewHolder qui représente chaque élément de la liste
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val threeDots: ImageView = itemView.findViewById(R.id.threeDots)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Afficher le PopupMenu lorsque les trois points sont cliqués
        holder.threeDots.setOnClickListener { view ->
            val popupMenu = PopupMenu(view.context, holder.threeDots)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.modifier -> {
                        Toast.makeText(view.context, "Modifiée", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.supprimer -> {
                        Toast.makeText(view.context, "Note supprimée", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int = itemCount
}

class CategorieActivity : AppCompatActivity() {

    lateinit var ui: ActivityCategorieBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityCategorieBinding.inflate(layoutInflater)
        setContentView(ui.root)
        setTitle("Categorie")

        // Récupérer la Toolbar
        val toolbar = ui.toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(getColor(R.color.white))

        // Initialiser le RecyclerView
        recyclerView = ui.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val numberOfItems = 4
        // Configurer l'adaptateur
        val adapter = MyAdapter(itemCount = 5)
        recyclerView.adapter = adapter
    }

    // Rattacher les icones au Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}