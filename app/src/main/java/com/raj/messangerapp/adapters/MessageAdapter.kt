package com.raj.messangerapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.raj.messangerapp.R
import com.raj.messangerapp.models.Messages

class MessageAdapter(val context:Context, val messageList:ArrayList<Messages>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            val view = LayoutInflater.from(context).inflate(R.layout.message_receiver_itemlist,parent,false)
            return ReceiverViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.message_sender_itemlist,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            viewHolder.sentText.text = currentMessage.message
        } else {
            val viewHolder = holder as ReceiverViewHolder
            viewHolder.ReceiveText.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if (FirebaseAuth.getInstance().currentUser!!.uid == currentMessage.senderId){
            return ITEM_SENT
        } else {
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sentText:TextView = itemView.findViewById(R.id.sender_message_text)
    }
    class ReceiverViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ReceiveText:TextView = itemView.findViewById(R.id.receiver_message_text)
    }
}