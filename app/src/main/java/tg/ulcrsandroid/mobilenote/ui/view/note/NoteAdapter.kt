package tg.ulcrsandroid.mobilenote.ui.view.note

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.mobilenote.R
import tg.ulcrsandroid.mobilenote.data.model.Note
import tg.ulcrsandroid.mobilenote.viewmodel.NoteViewModel

class NoteAdapter(
    private var noteList: List<Note>,
    private val noteViewModel: NoteViewModel,
    private val context: Context
): RecyclerView.Adapter<NoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false) // Layout spécifique à une note
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.noteDateTextView.text = note.titre
        holder.noteDateTextView.text = note.dateCreated.toString()


        holder.threeDots.setOnClickListener { view ->
            showPopupMenu(view, position)
        }


        holder.itemView.setOnClickListener {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra("noteId", note.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = noteList.size


    fun updateNotes(newNotes: List<Note>) {
        noteList = newNotes
        notifyDataSetChanged()
    }


    private fun showPopupMenu(view: View, position: Int) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.modifier -> {
                    // Action pour modifier une note
                    //val note = noteList[position]

                    true
                }
                R.id.supprimer -> {
                    // Supprimer une note via le ViewModel

                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}