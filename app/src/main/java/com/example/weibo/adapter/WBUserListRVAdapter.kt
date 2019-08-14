package com.example.weibo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weibo.R
import com.example.weibo.bean.WBUser
import com.example.weibo.holder.WBUserListVH

class WBUserListRVAdapter(private val users:ArrayList<WBUser>) : RecyclerView.Adapter<WBUserListVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WBUserListVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return WBUserListVH(parent.context,view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: WBUserListVH, position: Int) {
        holder.load(users[position])
    }
}