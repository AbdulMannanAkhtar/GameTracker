package com.example.gametracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gametracker.SignupActivity
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tagline = findViewById<View>(R.id.tagline)
        tagline.alpha = 0f
        tagline.translationX = 30f
        tagline.animate().alpha(1f).translationX(0f).setDuration(600).start()


        val gameTracker = findViewById<View>(R.id.Gametracker)
        gameTracker.alpha = 0f
        gameTracker.translationY = 40f
        gameTracker.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(100).start()


        val landingCard = findViewById<View>(R.id.landingCard)
        landingCard.alpha = 0f
        landingCard.translationY = 80f
        landingCard.animate().alpha(1f).translationY(0f).setDuration(700).setStartDelay(200).setInterpolator(DecelerateInterpolator()).start()


    }

    fun login(v: View)
    {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun signup(v: View)
    {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

}