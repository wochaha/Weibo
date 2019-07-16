package com.example.weibo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weibo.R
import com.example.weibo.bean.WBItem
import com.example.weibo.holder.WBItemSimpleHolder

class WBRecyclerViewAdapter(private val itemList:ArrayList<WBItem>) : RecyclerView.Adapter<WBItemSimpleHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WBItemSimpleHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weibo,parent,false)
        return WBItemSimpleHolder(parent.context,view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: WBItemSimpleHolder, position: Int) {
        holder.load(itemList[position])
    }
}