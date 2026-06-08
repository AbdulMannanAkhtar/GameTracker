package com.example.gametracker

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        intent = Intent()

        val password = findViewById<EditText>(R.id.password)
        val viewPassword1 = findViewById<ImageButton>(R.id.viewPassword1)

        passwordToggle(password, viewPassword1)
        passwordToggle(password, viewPassword1)


    }


    fun passwordToggle(password: EditText, toggle:ImageButton)
    {
        var isPassowrdVisiable = false

        toggle.setOnClickListener {
            isPassowrdVisiable = !isPassowrdVisiable

            if(isPassowrdVisiable)
            {
                password.transformationMethod = null

                toggle.contentDescription = "Hide Password"
            }
            else
            {
                password.transformationMethod = PasswordTransformationMethod.getInstance()

                toggle.contentDescription = "Show Password"
            }

            password.setSelection(password.text.length)

        }


    }
}
