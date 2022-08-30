package com.raj.messangerapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.raj.messangerapp.R
import com.raj.messangerapp.chats.ChatsActivity
import com.raj.messangerapp.models.User

class UserAdapter(val context: Context, val userList:ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_list_items,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.userName.text = userList[position].a_fullName
        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatsActivity::class.java)
            intent.putExtra("username",userList[position].a_fullName)
            intent.putExtra("uid",userList[position].e_userId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val userName:TextView = itemView.findViewById(R.id.user_text)
    }
}