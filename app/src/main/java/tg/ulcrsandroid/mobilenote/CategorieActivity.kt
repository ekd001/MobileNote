package tg.ulcrsandroid.mobilenote

import android.app.Dialog
import android.content.Intent
import android.media.RouteListingPreference
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
class MyAdapter(private val items: MutableList<String>, private val onItemClick: (position: Int) -> Unit) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // ViewHolder qui représente chaque élément de la liste
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val threeDots: ImageView = itemView.findViewById(R.id.threeDots)
        val noteTitle: TextView = itemView.findViewById(R.id.noteTitle) // Un TextView pour afficher le nom de la catégorie
        val noteDate: TextView = itemView.findViewById(R.id.noteDate)
        val itemContainer: View = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.noteTitle.text = items[position]

        holder.itemContainer.setOnClickListener {
            onItemClick(position) // Appelle le callback passé depuis l'activité
        }

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
                        items.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, items.size)
                        Toast.makeText(view.context, "Note supprimée", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int = items.size

    fun addItem(item: String) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

}

class CategorieActivity : AppCompatActivity() {

    lateinit var ui: ActivityCategorieBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private val notes = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityCategorieBinding.inflate(layoutInflater)
        setContentView(ui.root)
        setTitle("Categorie")

        // Récupérer la Toolbar
        val toolbar = ui.toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(getColor(R.color.white))

        ui.buttonAjouter.setOnClickListener {
            showAddDialog()
        }

        // Initialiser le RecyclerView
        recyclerView = ui.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurer l'adaptateur
        adapter = MyAdapter(notes) { position ->
            val intent = Intent(this, NoteSpace::class.java)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
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

        val editTextNom = view.findViewById<EditText>(R.id.editTextNom)
        val buttonValider = view.findViewById<Button>(R.id.buttonValider)

        buttonValider.setOnClickListener {
            val nom = editTextNom.text.toString()

            if (nom.isNotEmpty()) {
                adapter.addItem(nom)

                Toast.makeText(this, "Categorie $nom ajoutée", Toast.LENGTH_LONG).show()
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