package com.raj.messangerapp.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.raj.messangerapp.MainActivity
import com.raj.messangerapp.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        if (auth.currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
        }
        login_button.setOnClickListener {
            val email = login_email_edt.text.toString()
            val password = login_password_edt.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this,"Enter all detail",Toast.LENGTH_SHORT).show()
            }
        }
        login_register_button.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
    }
}