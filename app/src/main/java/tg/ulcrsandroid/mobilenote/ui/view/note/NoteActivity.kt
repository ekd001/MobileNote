package tg.ulcrsandroid.mobilenote.ui.view.note

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import tg.ulcrsandroid.mobilenote.R
import tg.ulcrsandroid.mobilenote.databinding.ActivityCategorieBinding
import tg.ulcrsandroid.mobilenote.databinding.ActivityMainBinding
import tg.ulcrsandroid.mobilenote.databinding.ActivityNoteBinding
import tg.ulcrsandroid.mobilenote.viewmodel.CategorieViewModel

class NoteActivity:AppCompatActivity(){
    lateinit var ui: ActivityNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(ui.root)
        setTitle("Note")

        // Récupérer la Toolbar
        val toolbar = ui.toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(getColor(R.color.white))
    }

    // Rattacher les icones au Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

}