package com.example.gametracker

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

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
       // passwordToggle(password, viewPassword1)

        val loginButton = findViewById<Button>(R.id.loginButton2)

        loginButton.setOnClickListener {
            userLogin()
        }


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

    fun userLogin()
    {
        val emailInput = findViewById<EditText>(R.id.email)
        val passwordInput = findViewById<EditText>(R.id.password)

        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if(email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try{
                SupabaseClient.client.auth.signInWith(Email)
                {
                    this.email = email
                    this.password = password
                }

                Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()

            }catch (e: Exception)
            {
                Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()

            }
        }
    }
}
