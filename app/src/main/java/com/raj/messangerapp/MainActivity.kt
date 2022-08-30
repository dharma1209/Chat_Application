package com.raj.messangerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.raj.messangerapp.adapters.UserAdapter
import com.raj.messangerapp.models.User
import com.raj.messangerapp.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var userList:ArrayList<User>
    private lateinit var userAdapter:UserAdapter
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = Firebase.database.reference.child("users")
        auth = Firebase.auth
        user_recyclerView.layoutManager = LinearLayoutManager(this)
        user_recyclerView.setHasFixedSize(true)
        userList = arrayListOf()

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val user = postSnapshot.getValue<User>()
                    if (user!!.e_userId != auth.currentUser!!.uid){
                        userList.add(user!!)
                    }

                }
                user_recyclerView.adapter = UserAdapter(this@MainActivity,userList)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext,"Error in fetching data",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profile ->{
                startActivity(Intent(this,ProfileActivity::class.java))
            }
            R.id.logout ->{
                auth.signOut()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}