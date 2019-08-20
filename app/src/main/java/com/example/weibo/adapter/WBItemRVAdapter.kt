package com.example.weibo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weibo.R
import com.example.weibo.bean.WBItem
import com.example.weibo.holder.WBItemFVHolder
import com.example.weibo.holder.WBItemSimpleHolder

/**
 * 后续需要在构造方法里面添加一个参数表示是否还有下一页
 * @param nextPage true 有下一页   false 没有下一页
 */
class WBItemRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private val last = -1
    private val notLast = 1
    var nextPage:Boolean = false

    private lateinit var list: ArrayList<WBItem>

    constructor()

    constructor(itemList: ArrayList<WBItem>, nextPage: Boolean = false){
        list = itemList
        this.nextPage = nextPage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View
        val holder:RecyclerView.ViewHolder
        if (viewType == last){
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_weibo_foot,parent,false)
            holder = WBItemFVHolder(view)
        }else{
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_weibo,parent,false)
            holder = WBItemSimpleHolder(parent.context,view)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return list.size+1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("position",position.toString())
        if (getItemViewType(position) == last){
            if (nextPage){
                (holder as WBItemFVHolder).load(WBItemFVHolder.LOAD_MORE)
            }else{
                (holder as WBItemFVHolder).load(WBItemFVHolder.NO_MORE)
            }
        }else{
            (holder as WBItemSimpleHolder).load(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount-1){
            last
        }else{
            notLast
        }
    }

    fun updateItems(itemList: ArrayList<WBItem>,nextPage:Boolean = false){
        this.nextPage = nextPage
        val backup = arrayListOf<WBItem>()
        backup.addAll(list)
        list.clear()
        list.addAll(itemList)
        list.addAll(backup)
        notifyDataSetChanged()
        backup.clear()
    }
}