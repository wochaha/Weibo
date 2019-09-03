package com.example.weibo.adapter

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weibo.R
import com.example.weibo.bean.WBComment
import com.example.weibo.holder.WBCommentHolder
import com.example.weibo.holder.WBItemorCommentFVHolder

class WBCommentRVAdapter(private val list:ArrayList<WBComment> = arrayListOf(), var nextPage:Boolean = false) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val last = -1
    private val notLast = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View
        val holder:RecyclerView.ViewHolder
        return if (viewType == last){
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_weibo_foot,parent,false)
            holder = WBItemorCommentFVHolder(view)
            holder
        }else{
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent,false)
            holder = WBCommentHolder(view)
            holder
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount-1){
            last
        }else{
            notLast
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("position",position.toString())
        if (getItemViewType(position) == last){
            if (nextPage){
                (holder as WBItemorCommentFVHolder).load(WBItemorCommentFVHolder.LOAD_MORE)
            }else{
                (holder as WBItemorCommentFVHolder).load(WBItemorCommentFVHolder.NO_MORE)
            }
        }else{
            (holder as WBCommentHolder).load(list[position])
        }
    }

    fun update(ls:ArrayList<WBComment>){
        addList(ls)
        notifyDataSetChanged()
    }

    private fun addList(ls:ArrayList<WBComment>){
        list.addAll(ls)
    }
}