package com.example.weibo.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weibo.R
import com.example.weibo.data.bean.WBUser
import com.example.weibo.ui.holder.WBUserListVH

class WBUserListRVAdapter : RecyclerView.Adapter<WBUserListVH>{
    private val users = arrayListOf<WBUser>()

    constructor()

    constructor(users:ArrayList<WBUser>){
        this.users.addAll(users)
    }

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

    fun setData(list:ArrayList<WBUser>){
        users.clear()
        users.addAll(list)
        notifyDataSetChanged()
        Log.d("WBUserListRVAdapter","数据源已更新")
    }

    fun reSet(){
        users.clear()
        notifyItemMoved(0,itemCount)
    }
}