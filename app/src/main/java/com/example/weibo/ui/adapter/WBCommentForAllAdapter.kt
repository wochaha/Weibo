package com.example.weibo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weibo.R
import com.example.weibo.data.bean.WBCommentRelatedUser
import com.example.weibo.ui.holder.WBCommentForAllVH
import com.example.weibo.ui.holder.WBItemorCommentFVHolder
import com.example.weibo.ui.holder.WBItemorCommentFVHolder.Companion.LOAD_MORE

class WBCommentForAllAdapter(private val list: ArrayList<WBCommentRelatedUser> = arrayListOf(),private var hasNextPage:Boolean = false)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val last = 1
    private val notLast = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View
        return if(viewType == last){
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_weibo_foot,parent,false)
            WBItemorCommentFVHolder(view)
        }else{
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_for_all,parent,false)
            WBCommentForAllVH(view)
        }
    }

    override fun getItemCount(): Int {
        return list.size+1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount-1){
            last
        }else{
            notLast
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == last){
            if (hasNextPage){
                (holder as WBItemorCommentFVHolder).load(LOAD_MORE)
            }else{
                (holder as WBItemorCommentFVHolder).load(LOAD_MORE)
            }
        }else{
            (holder as WBCommentForAllVH).load(list[position])
        }
    }

    fun update(ls:ArrayList<WBCommentRelatedUser>){
        addList(ls)
        notifyDataSetChanged()
    }

    private fun addList(ls:ArrayList<WBCommentRelatedUser>){
        list.addAll(ls)
    }
}