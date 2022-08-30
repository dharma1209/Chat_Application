package com.raj.messangerapp.chats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.raj.messangerapp.R
import com.raj.messangerapp.adapters.MessageAdapter
import com.raj.messangerapp.models.Messages
import kotlinx.android.synthetic.main.activity_chats.*

class ChatsActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var senderRoom:String
    private lateinit var receiverRoom:String
    private lateinit var database: DatabaseReference
    private lateinit var messageList: ArrayList<Messages>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
        val username = intent.getStringExtra("username")
        val receiverId = intent.getStringExtra("uid")
        supportActionBar?.title = username.toString()

        auth = Firebase.auth
        database = Firebase.database.reference
        val senderId = auth.currentUser?.uid
        senderRoom = receiverId + senderId
        receiverRoom = senderId + receiverId

        messageList = arrayListOf()
        chats_recyclerView.layoutManager =LinearLayoutManager(this)
        chats_recyclerView.setHasFixedSize(true)

        database.child("chats").child(senderRoom).child("messages").addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (data in snapshot.children){
                    val message = data.getValue<Messages>()
                    messageList.add(message!!)
                }
                chats_recyclerView.adapter = MessageAdapter(this@ChatsActivity,messageList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        send_message_button.setOnClickListener {
            val message = message_text.text.toString()
            if (message.isNotEmpty()){
                val msg = Messages(message,senderId)
                database.child("chats").child(senderRoom).child("messages").push()
                    .setValue(msg).addOnSuccessListener {
                        database.child("chats").child(receiverRoom).child("messages")
                            .push().setValue(msg)
                    }
            }
            message_text.setText("")
        }
    }
}