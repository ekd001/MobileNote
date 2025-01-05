package tg.ulcrsandroid.mobilenote

import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import tg.ulcrsandroid.mobilenote.databinding.ActivityCategorieBinding
import tg.ulcrsandroid.mobilenote.databinding.ActivityNoteSpaceBinding

class NoteSpace : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var ui: ActivityNoteSpaceBinding

        super.onCreate(savedInstanceState)
        ui = ActivityNoteSpaceBinding.inflate(layoutInflater)
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