package tg.ulcrsandroid.mobilenote.ui.view.categorie

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.mobilenote.R
import tg.ulcrsandroid.mobilenote.data.model.Categorie
import tg.ulcrsandroid.mobilenote.viewmodel.CategorieViewModel


class CategorieAdapter(private var categorieList: List<Categorie>,
                       private val categorieViewModel: CategorieViewModel,
                       private var context:Context
):RecyclerView.Adapter<CategorieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item, parent, false) //Layout specifique à une catégorie
        return CategorieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategorieViewHolder, position: Int) {
        val categorie = categorieList[position]
        holder.titreTextView.text = categorie.libelle

        holder.threeDots.setOnClickListener{view -> showPopupMenu(view, position)}
        holder.itemView.setOnClickListener{
            // Démarrer une autre activité
            val intent = Intent(context, CategorieActivity::class.java)
            intent.putExtra("categorieId", categorieList[position].id)
            // Passer des données supplémentaires si nécessaire
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = categorieList.size

    fun updateCategories(newCategories: List<Categorie>){
        categorieList = newCategories
        notifyDataSetChanged()
    }

    private fun showPopupMenu(view:View, position: Int){
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)

        popupMenu.setOnMenuItemClickListener{
            item: MenuItem -> when(item.itemId){
                R.id.modifier -> {
                    val selectCategorie = categorieList[position]
                    val old_libelle = selectCategorie.libelle
                    val old_description = selectCategorie.description
                    showUpdateDialog(libelle = old_libelle, description = old_description, categorie = selectCategorie)
                    true
                }
                R.id.supprimer -> {
                    categorieViewModel.deleteCategorie(categorieList[position])
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun showUpdateDialog(libelle:String, description:String, categorie: Categorie){
        // Créer un Dialog
        val dialog = Dialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.add_categorie, null)
        dialog.setContentView(view)

        val editTextLibelle = view.findViewById<EditText>(R.id.editTextNom)
        editTextLibelle.setText(libelle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        editTextDescription.setText(description)
        val buttonValider = view.findViewById<Button>(R.id.buttonValider)

        buttonValider.setOnClickListener{
            val new_libelle = editTextLibelle.text.toString()
            val new_description = editTextDescription.text.toString()

            if (new_libelle.isNotEmpty() && new_description.isNotEmpty()){
                categorieViewModel.updateCategorie(categorie.id,libelle,description)
                Toast.makeText(context, "Categorie $new_libelle a été modifiée", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }else{
                Toast.makeText(context, "Veuillez saisir un libellé et une description.", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_frame)

        dialog.window?.setLayout(
            (context.resources.displayMetrics.widthPixels * 0.7).toInt(), // 90% de la largeur de l'écran
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.show()
    }
}