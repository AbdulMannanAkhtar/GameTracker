package com.example.gametracker

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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



}