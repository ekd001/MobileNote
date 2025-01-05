package tg.ulcrsandroid.mobilenote.ui.view.note

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.mobilenote.R

class NoteViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    var noteTitleTextView: TextView = itemView.findViewById(R.id.noteTitle)
    var noteDateTextView: TextView = itemView.findViewById(R.id.noteDate)
    var threeDots: ImageView = itemView.findViewById(R.id.noteIcon)
}