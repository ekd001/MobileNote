package tg.ulcrsandroid.mobilenote

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import tg.ulcrsandroid.mobilenote.databinding.ActivityMainBinding
import tg.ulcrsandroid.mobilenote.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var ui: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(ui.root)

        // Rediriger vers MainActivity après 2 secondes
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Empêcher de revenir au Splash Screen
        }, 2000)
    }
}