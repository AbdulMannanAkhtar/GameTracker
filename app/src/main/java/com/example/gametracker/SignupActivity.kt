package com.example.gametracker

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private var passwordPopup: PopupWindow? = null


class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        intent = Intent()

        setupPasswordPopup()

        val password = findViewById<EditText>(R.id.password)
        val viewPassword1 = findViewById<ImageButton>(R.id.viewPassword1)

        val password2 = findViewById<EditText>(R.id.password2)
        val viewPassword2 = findViewById<ImageButton>(R.id.viewPassword2)

        passwordToggle(password, viewPassword1)
        passwordToggle(password2, viewPassword2)

        val signupButton = findViewById<Button>(R.id.SignupButton2)

        signupButton.setOnClickListener {
            createAccount()
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

    fun setupPasswordPopup() {
        val passwordInput = findViewById<EditText>(R.id.password)

        passwordInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showPasswordPopup(passwordInput)
            } else {
                passwordPopup?.dismiss()
            }

        }

        passwordInput.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                updatePasswordStrength(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}


        })


    }

    fun showPasswordPopup(anchor: View){
        if(passwordPopup?.isShowing == true)
        {
            return
        }

        val popupView = layoutInflater.inflate(R.layout.activity_popup, null)

        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED,)

        val popupHeight = popupView.measuredHeight

        passwordPopup = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false).apply{
            isOutsideTouchable = false
            elevation = 12f
        }

        passwordPopup?.showAsDropDown(anchor, 0, -(anchor.height + popupHeight + 40))

    }

    fun updatePasswordStrength(password: String){
        val popupView = passwordPopup?.contentView ?: return

        val progressBar =  popupView.findViewById<ProgressBar>(R.id.progressBar)

        var score = 0

        if(password.length >= 8)
        {
            score += 25
        }

        if(password.any {it.isDigit()})
        {
            score += 25
        }

        if (password.any {it.isLetter() })
        {
            score += 25
        }

        if (password.any {!it.isLetterOrDigit() })
        {
            score += 25
        }

        progressBar.progress = score

    }

    private fun createAccount()
    {
        val usernameInput = findViewById<EditText>(R.id.createUsername)
        val emailInput = findViewById<EditText>(R.id.email)
        val passwordInput = findViewById<EditText>(R.id.password)
        val confirmPasswordInput = findViewById<EditText>(R.id.password2)

        val username = usernameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()
        val passwordConfirm = confirmPasswordInput.text.toString()

        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty())
        {
            Toast.makeText(this, "Please fill all the fields to create account", Toast.LENGTH_SHORT).show()
            return
        }

        if(password != passwordConfirm)
        {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try{
                SupabaseClient.client.auth.signUpWith(Email)
                {
                    this.email = email
                    this.password = password
                }

                val userId = SupabaseClient.client.auth.currentUserOrNull()?.id

                if(userId == null)
                {
                    Toast.makeText(this@SignupActivity, "Error! Signup failed", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                SupabaseClient.client.postgrest["profiles"].insert(
                    Profile(
                        id = userId,
                        username = username
                    )
                )
                Toast.makeText(this@SignupActivity, "Account created successfully!", Toast.LENGTH_SHORT).show()

            } catch (e: Exception)
            {
                //Log.e("SIGNUP", "signup failure", e)
                Toast.makeText(this@SignupActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }


    }



}