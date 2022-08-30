package com.raj.messangerapp.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.raj.messangerapp.R
import com.raj.messangerapp.models.ProfilePhoto
import com.raj.messangerapp.models.User
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val REQUEST_CODE = 100
    private lateinit var file:Uri
    private lateinit var storageReference: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = Firebase.auth
        database = Firebase.database.reference
        storageReference = Firebase.storage.reference
        database.child("users").child(auth.currentUser!!.uid).get().addOnSuccessListener {
            val profile = it.getValue<User>()
            user_fullName.text = profile!!.a_fullName
            user_email.text = profile.b_email
            user_gender.text = profile.d_gender
        }.addOnFailureListener{
            Toast.makeText(this,"Error in fetching profile",Toast.LENGTH_SHORT).show()
        }
        storageReference.child("profile").child(auth.currentUser!!.uid).child("p_photo").downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).into(user_profile_photo)
        }


        updateImage_button.setOnClickListener {
            openGalleryForImage()
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            user_profile_photo.setImageURI(data?.data)
             file = data?.data!!
            storageReference.child("profile").child(auth.currentUser!!.uid).child("p_photo").putFile(file).addOnSuccessListener {
                Toast.makeText(this,"file uploaded successfully",Toast.LENGTH_SHORT).show()
            }
        }
    }
}