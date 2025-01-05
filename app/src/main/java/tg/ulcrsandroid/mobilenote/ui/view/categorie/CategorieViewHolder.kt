package tg.ulcrsandroid.mobilenote.ui.view.categorie

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.mobilenote.R

class CategorieViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
    val titreTextView: TextView = itemView.findViewById(R.id.categorieTitle)
    val threeDots: ImageView = itemView.findViewById(R.id.threeDots)
}