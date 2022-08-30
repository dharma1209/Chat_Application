package com.raj.messangerapp.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.raj.messangerapp.R
import com.raj.messangerapp.models.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = Firebase.auth

        register_button.setOnClickListener {
            val fullName = register_fullName_edt.text.toString()
            val email = register_email_edt.text.toString()
            val password = register_password_edt.text.toString()
            val gender = gender_edt.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && fullName.isNotEmpty() && gender.isNotEmpty()){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            updateUI(fullName,email,password,gender)
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this,"Please enter detail",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun updateUI(_fullName: String, _email: String, _password: String, _gender: String) {
        database = Firebase.database.reference
        val newUser = User(_fullName,_email,_password,_gender,auth.currentUser?.uid)

        database.child("users").child(auth.currentUser!!.uid).setValue(newUser).addOnSuccessListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}